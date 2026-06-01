<template>
	<view class="content">
		<view class="header-bg">
			<view class="user-info">
				<view class="avatar-box">
					<image src="/static/myImages/morentouxiang.png" mode="aspectFill"></image>
				</view>
				<navigator url="/pages/tables/login" open-type="navigate" class="info-text">
					<text class="username">{{getTitle}}</text>
					<text class="subtitle">{{username.length > 0 ? '欢迎使用"放心用"App' : '点击登录，体验完整功能'}}</text>
				</navigator>
			</view>
		</view>

		<view class="floating-box">
			<view class="action-card" @click="goToInformationModify()">
				<image class="card-icon" src="/static/myImages/ziliaoxiugai.png" mode="aspectFit"></image>
				<view class="card-text">
					<text class="title">资料修改</text>
					<text class="desc">更新个人资质</text>
				</view>
			</view>
			<view class="divider"></view>
			<view class="action-card" @click="goToRepayHistory()">
				<image class="card-icon" src="/static/myImages/huankuanpingzeng.png" mode="aspectFit"></image>
				<view class="card-text">
					<text class="title">还款记录</text>
					<text class="desc">查看历史账单</text>
				</view>
			</view>
		</view>

		<view class="menu-section">
			<view class="menu-group">
				<view class="menu-item" @click="goToStatus()">
					<view class="item-left">
						<view class="iconfont icon-help item-icon text-blue"></view>
						<text class="item-name">进度查询</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
				
				<view class="menu-item" @click="goToProblems()">
					<view class="item-left">
						<view class="iconfont icon-tishi item-icon text-orange"></view>
						<text class="item-name">常见问题</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
				
				<view class="menu-item" @click="goToInformation()">
					<view class="item-left">
						<view class="iconfont icon-user item-icon text-green"></view>
						<text class="item-name">个人信息完善</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
				
				<view class="menu-item" @click="goToAccountModify()">
					<view class="item-left">
						<view class="iconfont icon-gerenzhongxin-zhihui item-icon text-purple"></view>
						<text class="item-name">账号信息修改</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
			</view>

			<view class="menu-group">
				<view class="menu-item" @click="goToPrivates()">
					<view class="item-left">
						<view class="iconfont icon-zaixianyanzi item-icon text-cyan"></view>
						<text class="item-name">用户隐私政策条款</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
				
				<view class="menu-item" @click="goToAboutApp()">
					<view class="item-left">
						<view class="iconfont icon-kaitongfuwu item-icon text-red"></view>
						<text class="item-name">关于APP</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
				
				<view class="menu-item" @click="goToCache()">
					<view class="item-left">
						<view class="iconfont icon-ashbin item-icon text-gray"></view>
						<text class="item-name">清除缓存</text>
					</view>
					<view class="iconfont icon-arrow-right arrow-icon"></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				title:'立即登录',
				username:''
			};
		},
		
		onShow(){
			const app=getApp()
			this.username=app.globalData.username
		},
		
		methods:{
			goToAccountModify(){ uni.navigateTo({ url:"/pages/tables/accoountModify" }) },
			goToPrivates(){ uni.navigateTo({ url:"/pages/tables/privates" }) },
			goToAboutApp(){ uni.navigateTo({ url:"/pages/tables/aboutApp" }) },
			goToProblems(){ uni.navigateTo({ url:"/pages/tables/Problems" }) },
			goToInformation(){ uni.navigateTo({ url:"/pages/tables/information" }) },
			goToRepayHistory(){ uni.navigateTo({ url:"/pages/tables/repayHistory" }) },
			goToStatus(){ uni.navigateTo({ url:"/pages/tables/status" }) },
			goToCache(){ uni.navigateTo({ url:"/pages/tables/cache" }) },
			goToInformationModify(){ uni.navigateTo({ url:"/pages/tables/informationModify" }) }
		},
		
		computed:{
			getTitle(){
				if(this.username && this.username.length > 0){
					return `你好! ${this.username}`
				} else {
					return this.title
				}
			}
		}
	}
</script>

<style scoped lang="scss">
	/* 基础设定 */
	.content {
		min-height: 100vh;
		background-color: #F4F6F9; /* 统一的浅灰底色 */
		padding-bottom: 40rpx;
	}

	/* 顶部红底与个人信息 */
	.header-bg {
		background: linear-gradient(135deg, #e63946 0%, #c1121f 100%);
		padding: 120rpx 40rpx 140rpx 40rpx; /* 底部留白给卡片上浮 */
		border-bottom-left-radius: 50rpx;
		border-bottom-right-radius: 50rpx;
		box-shadow: 0 4rpx 20rpx rgba(193, 18, 31, 0.2);
		
		.user-info {
			display: flex;
			align-items: center;
			gap: 30rpx;
			
			.avatar-box {
				width: 120rpx;
				height: 120rpx;
				border-radius: 50%;
				background-color: #ffffff;
				display: flex;
				align-items: center;
				justify-content: center;
				box-shadow: 0 6rpx 16rpx rgba(0,0,0,0.1);
				border: 4rpx solid rgba(255, 255, 255, 0.5);
				
				image {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}
			
			.info-text {
				display: flex;
				flex-direction: column;
				gap: 10rpx;
				
				.username {
					color: #ffffff;
					font-size: 44rpx;
					font-weight: bold;
					letter-spacing: 2rpx;
				}
				
				.subtitle {
					color: rgba(255, 255, 255, 0.8);
					font-size: 24rpx;
					background: rgba(0, 0, 0, 0.15);
					padding: 4rpx 16rpx;
					border-radius: 20rpx;
					display: inline-block;
				}
			}
		}
	}
	
	/* 核心悬浮卡片 */
	.floating-box {
		width: 690rpx;
		height: 180rpx;
		margin: -90rpx auto 0;
		background-color: #ffffff;
		border-radius: 24rpx;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.06);
		position: relative;
		z-index: 10;
		display: flex;
		align-items: center;
		padding: 0 10rpx;
		box-sizing: border-box;
		
		.divider {
			width: 2rpx;
			height: 80rpx;
			background-color: #f0f0f0;
		}
		
		.action-card {
			flex: 1;
			display: flex;
			align-items: center;
			justify-content: center;
			gap: 20rpx;
			height: 100%;
			transition: opacity 0.2s;
			
			&:active {
				opacity: 0.7;
			}
			
			.card-icon {
				width: 76rpx;
				height: 76rpx;
			}
			
			.card-text {
				display: flex;
				flex-direction: column;
				gap: 4rpx;
				
				.title {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
				}
				
				.desc {
					font-size: 22rpx;
					color: #999;
				}
			}
		}
	}
	
	/* 菜单列表区 */
	.menu-section {
		padding: 0 30rpx;
		margin-top: 40rpx;
		
		.menu-group {
			background-color: #ffffff;
			border-radius: 24rpx;
			padding: 0 30rpx;
			margin-bottom: 30rpx;
			box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.02);
			
			.menu-item {
				display: flex;
				justify-content: space-between;
				align-items: center;
				height: 110rpx;
				border-bottom: 1rpx solid #f5f5f5;
				transition: background-color 0.2s;
				
				&:active {
					background-color: #fafafa;
				}
				
				/* 消除最后一项的下划线 */
				&:last-child {
					border-bottom: none;
				}
				
				.item-left {
					display: flex;
					align-items: center;
					gap: 20rpx;
					
					.item-icon {
						font-size: 44rpx;
					}
					
					/* 为不同图标配置主题色，增加页面活力 */
					.text-blue { color: #0077b6; }
					.text-orange { color: #f77f00; }
					.text-green { color: #2a9d8f; }
					.text-purple { color: #7209b7; }
					.text-cyan { color: #00b4d8; }
					.text-red { color: #e63946; }
					.text-gray { color: #999999; }
					
					.item-name {
						font-size: 32rpx;
						color: #333;
						font-weight: 500;
					}
				}
				
				.arrow-icon {
					font-size: 32rpx;
					color: #cccccc;
				}
			}
		}
	}
</style>