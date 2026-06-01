<template>
	<view class="content">
		<view class="header-section">
			<view class="title-row">我的贷款</view>
			
			<view class="status-box" v-if="active">
				<text class="status-text">{{text}}</text>
				<button class="login-btn" @click="goToLogin()">立即登录</button>
			</view>
			
			<view class="status-box" v-if="notActive">
				<text class="status-text">{{text}}</text>
				<view class="score-display">
					<text class="score-value">{{creditScore}}</text>
					<text class="score-unit">分</text>
				</view>
			<button class="view-report-btn" @click="viewReport()">查看风控评估报告</button>
				<text class="score-hint">保持良好信用，可享受快速放款服务</text>
			</view>
		</view>

		<view class="floating-actions">
			<view class="action-item" @click="goToLoanHistory()">
				<image class="action-img" src="/static/myImages/daikuanjilu.png" mode="aspectFit"></image>
			</view>
			<view class="action-item" @click="goToStatus()">
				<image class="action-img" src="/static/myImages/shenqingjindu.png" mode="aspectFit"></image>
			</view>
			<view class="action-item" @click="goToRepayHistory()">
				<image class="action-img" src="/static/myImages/huankuanjilu.png" mode="aspectFit"></image>
			</view>
		</view>

		<view class="service-section">
			<text class="section-title">贷款服务</text>
			
			<view class="service-grid">
				<view class="service-card theme-orange" @click="goToProduct()">
					<view class="card-info">
						<text class="card-title">贷款申请</text>
						<text class="card-desc">完善资料 在线申请</text>
					</view>
					<view class="iconfont icon-tijiaoyanzi watermark-icon"></view>
				</view>
				
				<view class="service-card theme-blue" @click="goToRepayPlan()">
					<view class="card-info">
						<text class="card-title">还款计划</text>
						<text class="card-desc">查看还款计划</text>
					</view>
					<view class="iconfont icon-qianshuxieyi watermark-icon"></view>
				</view>
				
				<view class="service-card theme-green" @click="goToOverDue()">
					<view class="card-info">
						<text class="card-title">逾期记录</text>
						<text class="card-desc">及时还款 避免逾期</text>
					</view>
					<view class="iconfont icon-zaixianyanzi watermark-icon"></view>
				</view>
				
				<view class="service-card theme-cyan" @click="goToInformation()">
					<view class="card-info">
						<text class="card-title">账户信息</text>
						<text class="card-desc">随时掌握账户信息</text>
					</view>
					<view class="iconfont icon-user watermark-icon"></view>
				</view>
				
				<view class="service-card theme-red" @click="goToHelp()">
					<view class="card-info">
						<text class="card-title">还款帮助</text>
						<text class="card-desc">常见还款疑难解答</text>
					</view>
					<view class="iconfont icon-zhifudingjin watermark-icon"></view>
				</view>
				
				<view class="service-card theme-purple" @click="goToRepay()">
					<view class="card-info">
						<text class="card-title">进行还款</text>
						<text class="card-desc">一键进行当期还款</text>
					</view>
					<view class="iconfont icon-anquanbaozhang watermark-icon"></view>
				</view>
			</view>
		</view>
		<view class="report-modal-mask" v-if="showReportModal" @click="closeReport">
					<view class="report-modal-content" @click.stop>
						<view class="modal-header">
							<text class="modal-title">风险评估报告</text>
							<text class="close-icon" @click="closeReport">×</text>
						</view>
						<scroll-view scroll-y="true" class="modal-body">
							<view v-if="loadingReport" class="loading-box">
								<view class="spinner"></view>
								<text class="loading-text">报告生成中...</text>
							</view>
							<text class="report-text" v-else>{{reportContent || '暂无评估数据'}}</text>
						</scroll-view>
						<view class="modal-footer">
							<button class="confirm-btn" @click="closeReport">确认</button>
						</view>
					</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				creditScore:0,
				active:true,
				notActive:false,
				text:"登陆后查看信用积分",
				showReportModal: false,
				reportContent: '',
				loadingReport: false
						};
					},
		
		onShow(){
			this.getCreditScore()
		},
		
		methods:{
			async viewReport() {
							console.log("【Debug】查看报告按钮被点击了！");
							    uni.showToast({ title: '按钮已响应', icon: 'none' });
							
							    this.showReportModal = true;
							    this.loadingReport = true;
							try {
								// 这里的接口路径根据你后端 Controller 的定义
								let res = await uni.$http.get('/ai/get_report', {});
								if(res.code == 200) {
									this.reportContent = res.data;
								} else {
									uni.showToast({ title: res.msg || '获取失败', icon: 'none' });
								}
							} catch (e) {
								uni.showToast({ title: '网络异常', icon: 'none' });
							} finally {
								this.loadingReport = false;
							}
						},
						
			closeReport() {
				this.showReportModal = false;
			},
			
			async getCreditScore(){
				if(getApp().globalData.username && getApp().globalData.username.length != 0){
					try {
						let res = await uni.$http.get('/other/score',{})
						
						if(res.code == 200){
							this.creditScore = res.data.creditScore
							this.active = false
							this.notActive = true
							this.text = "您当前的信用评估得分为"
						} else {
							// 如果后端返回非200（比如被拦截器挡住），直接按过期处理
							this.handleTokenExpired();
						}
					} catch (e) {
						// 🌟 核心修改：如果后端因为 Token 过期直接抛出 500 异常崩溃，请求会走入 catch
						// 我们在这里温柔地接住异常，不让前端报错，而是跳转去登录
						this.handleTokenExpired();
					}
				}
			},
			
			// 🌟 统一处理 Token 过期/无效的逻辑
			handleTokenExpired() {
				uni.showToast({
					title: '登录已过期，请重新登录',
					icon: 'none',
					duration: 1500
				});
				
				getApp().globalData.username = '';
				getApp().globalData.token = '';
				uni.removeStorageSync('username');
				uni.removeStorageSync('token');
				
				this.active = true;
				this.notActive = false;
				this.text = "登陆后查看信用积分";
				this.creditScore = 0;
				
				setTimeout(() => {
					uni.navigateTo({
						url: "/pages/tables/login"
					})
				}, 1000);
			},
			
			// 路由跳转
			goToHelp(){ uni.navigateTo({ url:"/pages/tables/help" }) },
			goToRepayHistory(){ uni.navigateTo({ url:"/pages/tables/repayHistory" }) },
			goToStatus(){ uni.navigateTo({ url:"/pages/tables/status" }) },
			goToLogin(){ uni.navigateTo({ url:"/pages/tables/login" }) },
			goToInformation(){ uni.navigateTo({ url:"/pages/tables/information" }) },
			goToLoanHistory(){ uni.navigateTo({ url:"/pages/tables/loanHistory" }) },
			goToRepayPlan(){ uni.navigateTo({ url:"/pages/tables/repayPlan" }) },
			goToOverDue(){ uni.navigateTo({ url:"/pages/tables/overDue" }) },
			goToProduct(){ uni.navigateTo({ url:"/pages/product/product" }) },
			goToRepay(){ uni.navigateTo({ url:"/pages/tables/repay" }) }
		}
	}
</script>

<style scoped lang="scss">
	/*----------------------------------新增--------------------------------------*/
	/* 1. 顶部红色区域内的按钮 */
	.view-report-btn {
		margin: 20rpx 0;
		width: 320rpx;
		height: 64rpx;
		line-height: 64rpx;
		font-size: 26rpx;
		color: #ffffff;
		background: rgba(255, 255, 255, 0.2); /* 半透明效果 */
		border: 1rpx solid rgba(255, 255, 255, 0.4);
		border-radius: 32rpx;
		position: relative;
		z-index: 99;
		&::after { border: none; }
		&:active { background: rgba(255, 255, 255, 0.3); }
	}
	
	/* 2. 报告弹窗遮罩 */
	.report-modal-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0, 0, 0, 0.6);
		z-index: 9999;
		display: flex;
		align-items: center;
		justify-content: center;
		backdrop-filter: blur(4px); /* 背景模糊，显高级 */
	}
	
	/* 3. 弹窗主体 */
	.report-modal-content {
		width: 640rpx;
		max-height: 900rpx;
		background-color: #ffffff;
		border-radius: 32rpx;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		animation: modalSlideUp 0.3s ease-out;
	}
	
	@keyframes modalSlideUp {
		from { transform: translateY(100rpx); opacity: 0; }
		to { transform: translateY(0); opacity: 1; }
	}
	
	.modal-header {
		background: linear-gradient(135deg, #e63946 0%, #c1121f 100%);
		padding: 30rpx 40rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		
		.modal-title {
			color: #ffffff;
			font-size: 34rpx;
			font-weight: bold;
		}
		.close-icon {
			color: #ffffff;
			font-size: 48rpx;
			line-height: 1;
		}
	}
	
	.modal-body {
		flex: 1;
		padding: 40rpx;
		box-sizing: border-box;
		height: 600rpx;
		
		.report-text {
			font-size: 28rpx;
			color: #333;
			line-height: 1.8;
			white-space: pre-wrap; /* 识别换行 */
		}
		
		.loading-box {
			height: 100%;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			
			.spinner {
				width: 60rpx;
				height: 60rpx;
				border: 4rpx solid #f3f3f3;
				border-top: 4rpx solid #e63946;
				border-radius: 50%;
				animation: spin 1s linear infinite;
				margin-bottom: 20rpx;
			}
		}
	}
	
	.modal-footer {
		padding: 20rpx 40rpx 40rpx;
		.confirm-btn {
			background: #e63946;
			color: #fff;
			border-radius: 40rpx;
			font-size: 30rpx;
			&::after { border: none; }
		}
	}
	
	@keyframes spin {
		0% { transform: rotate(0deg); }
		100% { transform: rotate(360deg); }
	}
	/*----------------------------------新增--------------------------------------*/
	
	
	/* 基础设定 */
	.content {
		background-color: #F4F6F9; /* 统一浅灰底色 */
		min-height: 100vh;
		padding-bottom: 40rpx;
	}
	
	/* 顶部红底区域 */
	.header-section {
		background: linear-gradient(135deg, #e63946 0%, #c1121f 100%);
		padding: 85rpx 40rpx 120rpx 40rpx;
		border-bottom-left-radius: 50rpx;
		border-bottom-right-radius: 50rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		box-shadow: 0 4rpx 20rpx rgba(193, 18, 31, 0.2);
		
		.title-row {
		        width: 100%;
		        font-size: 36rpx;
		        color: #ffffff;
		        font-weight: 600;
		        text-align: center; /* <--- 将这里的 left 改为 center */
		        margin-bottom: 40rpx;
		    }
		
		.status-box {
			display: flex;
			flex-direction: column;
			align-items: center;
			width: 100%;
			
			.status-text {
				font-size: 28rpx;
				color: rgba(255, 255, 255, 0.85);
				margin-bottom: 10rpx;
			}
			
			/* 未登录状态的按钮 */
			.login-btn {
				width: 280rpx;
				height: 80rpx;
				line-height: 80rpx;
				color: #e63946;
				background-color: #ffffff;
				font-size: 32rpx;
				font-weight: bold;
				border-radius: 40rpx;
				margin-top: 20rpx;
				border: none;
				box-shadow: 0 6rpx 12rpx rgba(0,0,0,0.1);
				&::after { border: none; }
				&:active { transform: scale(0.96); }
			}
			
			/* 已登录状态的分数展示 */
			.score-display {
				display: flex;
				align-items: baseline;
				justify-content: center;
				margin-bottom: 10rpx;
				
				.score-value {
					font-size: 80rpx;
					font-weight: bold;
					color: #ffffff;
					letter-spacing: 2rpx;
				}
				.score-unit {
					font-size: 30rpx;
					color: #ffffff;
					margin-left: 8rpx;
				}
			}
			
			.score-hint {
				font-size: 22rpx;
				color: rgba(255, 255, 255, 0.7);
				background: rgba(0, 0, 0, 0.15);
				padding: 6rpx 20rpx;
				border-radius: 20rpx;
			}
		}
	}
	
	/* 悬浮图标区 */
	.floating-actions {
		width: 690rpx;
		height: 180rpx;
		background-color: #ffffff;
		margin: -70rpx auto 0;
		border-radius: 24rpx;
		position: relative;
		z-index: 10;
		display: flex;
		justify-content: space-around;
		align-items: center;
		box-shadow: 0 8rpx 24rpx rgba(0,0,0,0.06);
		padding: 0 20rpx;
		box-sizing: border-box;
		
		.action-item {
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			
			.action-img {
				width: 110rpx;
				height: 110rpx;
			}
			&:active { opacity: 0.7; }
		}
	}
	
	/* 底部服务宫格 */
	.service-section {
		padding: 40rpx 30rpx;
		
		.section-title {
			display: block;
			font-size: 34rpx;
			font-weight: bold;
			color: #333;
			margin-bottom: 24rpx;
			padding-left: 10rpx;
		}
		
		/* 核心：采用 Grid 网格布局解决屏幕适配问题 */
		.service-grid {
			display: grid;
			grid-template-columns: repeat(2, 1fr);
			gap: 20rpx;
		}
		
		.service-card {
			height: 170rpx;
			border-radius: 20rpx;
			padding: 24rpx;
			box-sizing: border-box;
			position: relative;
			overflow: hidden;
			display: flex;
			flex-direction: column;
			justify-content: flex-start;
			transition: all 0.2s;
			
			&:active { transform: scale(0.97); }
			
			.card-info {
				display: flex;
				flex-direction: column;
				z-index: 2; /* 确保文字在图标上方 */
				
				.card-title {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
					margin-bottom: 8rpx;
				}
				
				.card-desc {
					font-size: 22rpx;
					color: #777;
				}
			}
			
			/* 右下角大图标，作半透明水印处理 */
			.watermark-icon {
				position: absolute;
				right: -10rpx;
				bottom: -15rpx;
				font-size: 90rpx;
				opacity: 0.25;
				z-index: 1;
			}
		}
		
		/* 加深后的现代化清爽配色方案（提升对比度） */
				.theme-orange {
					background: linear-gradient(135deg, #FFEDD6, #FFD9A8);
					.watermark-icon { color: #E66A00; }
				}
				
				.theme-blue {
					background: linear-gradient(135deg, #E6F2FF, #BCDDFE);
					.watermark-icon { color: #006699; }
				}
				
				.theme-green {
					background: linear-gradient(135deg, #E3FBEA, #BAEBCB);
					.watermark-icon { color: #208A7E; }
				}
				
				.theme-cyan {
					background: linear-gradient(135deg, #E0F7F7, #B7EAEA);
					.watermark-icon { color: #009CBF; }
				}
				
				.theme-red {
					background: linear-gradient(135deg, #FFE4E4, #FFC4C5);
					.watermark-icon { color: #D12F3B; }
				}
				
				.theme-purple {
					background: linear-gradient(135deg, #EFE6FF, #D7BFFF);
					.watermark-icon { color: #6200A3; }
				}
	}
</style>