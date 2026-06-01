<template>
	<view class="content">
			<view class="up">
				<view class="iconfont icon-fanhui" @click="goToLoan()"></view>
				<text>进行还款</text>
			</view>
			<view class="down">
				<view class="header">
					<view class="summary-card">
						<view class="summary-item">
							<text class="label">本期应还(元)</text>
							<text class="num amount-font">{{ getNextRepayAmount() }}</text>
						</view>
						<view class="line"></view>
						<view class="summary-item">
							<text class="label">剩余期数</text>
							<text class="num">{{ getUnpaidCount() }}</text>
						</view>
					</view>
				</view>
	
				<view class="list-container">
	
					<block v-if="planList.length > 0">
						<view class="plan-card" v-for="(item, index) in planList" :key="item.id"
							:class="{ 'card-done': item.status }">
	
							<view class="card-header">
								<view class="header-left">
									<view class="period-title">
										<text class="icon">🗓</text>
										<text class="text">第 {{ item.currentPeriod }} 期</text>
									</view>
									<text class="contract-text">合同号: {{ item.contractId }}</text>
								</view>
	
								<view class="status-tag" :class="item.status ? 'tag-gray' : 'tag-red'">
									{{ item.status ? '已还款' : '待还款' }}
								</view>
							</view>
	
							<view class="divider"></view>
	
							<view class="card-body">
															<view class="main-row">
																<view class="info-block">
																	<text class="label">应还总额</text>
																	<text class="value big-amount">¥ {{ formatMoney(item.priIntAll) }}</text>
																</view>
																<view class="info-block align-right">
																	<text class="label">截至日期</text>
																	<text class="value date-text">{{ formatDate(item.endTime) }}</text>
																</view>
															</view>
								
															<view class="sub-row">
																<text class="sub-item">本金 {{ formatMoney(item.repayPrincipal) }}</text>
																<text class="sub-divider">|</text>
																<text class="sub-item">利息 {{ formatMoney(item.repayInterest) }}</text>
																<text class="sub-divider">|</text>
																<text class="sub-item">借款人: {{ item.name }} (ID: {{ item.userId }})</text>
															</view>
								
															<view class="warning-row" v-if="!item.status && item.overdueInterest > 0">
																<text class="warn-icon">⚠️</text>
																<text class="warn-text">已产生逾期罚息: ¥{{ formatMoney(item.overdueInterest) }}</text>
															</view>
														</view>
	
							<!-- 已还款显示印章 -->
							<view v-if="item.status" class="stamp">已结清</view>
	
							<!-- 未还款显示操作按钮 -->
							<view v-else class="card-action">
								<button class="repay-btn" @click="handleRepay(item)">立即还款</button>
							</view>
	
						</view>
						<view class="no-more">- 暂无更多计划 -</view>
					</block>
	
					<block v-else-if="!loading">
						<view class="empty-state">
							<text class="empty-icon">📅</text>
							<text class="empty-text">暂无还款计划</text>
						</view>
					</block>
	
				</view>
			</view>
		</view>
</template>

<script>
	export default {
			data() {
				return {
					loading: true,
					planList: []
				};
			},
			onLoad() {
				this.getPlanData();
			},
			methods: {
				goToLoan() {
					uni.navigateBack({
										delta:1
									})
				},
				// 点击还款按钮
								async handleRepay(item) {
									uni.showModal({
										title: '确认还款',
										content: `您即将支付第 ${item.currentPeriod} 期账单，金额 ¥${item.priIntAll}`,
										confirmColor: '#b1141d',
										// 🌟 修复 1：这里的回调函数必须加上 async
										success: async (res) => {
											if (res.confirm) {
												uni.showLoading({
													title: '支付中...',
													mask: true // 加个遮罩防止狂点
												});
												
												try {
													// 🌟 修复 2：必须加上 await 等待后端响应！
													let res1 = await uni.$http.post('/repay', {
														id: item.id,
														repayMethod: "微信"
													});
													
													// 关闭 loading 圈圈
													uni.hideLoading();
													
													if(res1.code == 200) {
														// 🌟 修复 3：加一个 100 毫秒的微小延迟，防止被 hideLoading 吞掉弹窗
														setTimeout(() => {
															uni.showToast({
																title: '还款成功',
																icon: 'success',
																duration: 1500
															});
														}, 100);
														
														// 🌟 支付成功后自动刷新列表，让印章盖上去
														this.getPlanData();
														
													} else {
														// 后端返回错误的情况
														setTimeout(() => {
															uni.showToast({
																title: res1.msg || '还款失败',
																icon: 'none'
															});
														}, 100);
													}
												} catch(e) {
													// 异常兜底，比如断网
													uni.hideLoading();
													setTimeout(() => {
														uni.showToast({ title: '网络异常，请重试', icon: 'none' });
													}, 100);
												}
											}
										}
									});
								},
				async getPlanData() {
					uni.showLoading({
						title: '加载中...'
					});
	
					// setTimeout(() => {
						// const mockData = [{
						// 		"id": 20,
						// 		"name": "张三",
						// 		"contractId": 2356,
						// 		"currentPeriod": 6,
						// 		"repayPrincipal": 2000,
						// 		"repayInterst": 200,
						// 		"repayAll": 2200,
						// 		"repayDate": "2025-08-08 09:00:00",
						// 		"status": true,
						// 		"overdueInterst": 0
						// 	},
						// 	{
						// 		"id": 23,
						// 		"name": "张三",
						// 		"contractId": 2356,
						// 		"currentPeriod": 7,
						// 		"repayPrincipal": 2000,
						// 		"repayInterst": 200,
						// 		"repayAll": 2200,
						// 		"repayDate": "2025-09-08 09:05:37",
						// 		"status": false,
						// 		"overdueInterst": 200
						// 	},
						// 	{
						// 		"id": 99,
						// 		"name": "张三",
						// 		"contractId": 8888,
						// 		"currentPeriod": 1,
						// 		"repayPrincipal": 5000,
						// 		"repayInterst": 50,
						// 		"repayAll": 5100,
						// 		"repayDate": "2025-10-01 09:00:00",
						// 		"status": false,
						// 		"overdueInterst": 0
						// 	}
						// ];
	
						// this.planList = mockData;
						
						let res=await uni.$http.get('/repay',{})
						
						if(res.code==200){
							const mockData=res.data
							this.planList=mockData
						}
						
						this.loading = false;
						uni.hideLoading();
					// }, 500);
				},
	
				formatDate(dateStr) {
									if (!dateStr) return '--';
									return dateStr.replace('T', ' ').split(' ')[0];
								},
	
				formatMoney(num) {
					return Number(num).toLocaleString();
				},
	
				getUnpaidCount() {
					return this.planList.filter(item => !item.status).length;
				},
	
				// 🌟 修改点 9：顶部统计里的 repayAll 改为 priIntAll
								getNextRepayAmount() {
									const nextItem = this.planList.find(item => !item.status);
									return nextItem ? this.formatMoney(nextItem.priIntAll) : '0.00';
								}
			}
		};
	</script>
	
	<style scoped lang="less">
		.content {
				min-height: 100vh;
				background-color: #f7f7f7;
			}
		
			.up {
				box-sizing: border-box;
				padding-top: 75rpx;
				margin-bottom: 20rpx;
		
				position: fixed;
				display: flex;
				align-items: center;
		
				z-index: 999;
				width: 750rpx;
				height: 150rpx;
				background-color: #ffffff;
		
				box-shadow: 0 5rpx 15rpx rgba(0, 0, 0, 0.05);
		
				.iconfont {
					font-size: 45rpx;
				}
		
				text {
					display: block;
					font-size: 35rpx;
					margin-left: 270rpx;
				}
			}
		
			.down {
				padding-top: 180rpx;
				position: relative;
				z-index: 1;
			}
		
			.header {
				position: relative;
				height: 60rpx;
				margin-bottom: 80rpx;
			}
		
		
			.summary-card {
				position: absolute;
				bottom: -60rpx;
				left: 40rpx;
				right: 40rpx;
				height: 120rpx;
				background-color: #ffffff;
				border-radius: 16rpx;
				box-shadow: 0 8rpx 20rpx rgba(177, 20, 29, 0.1);
				display: flex;
				align-items: center;
				justify-content: space-around;
				z-index: 10;
			}
		
			.summary-item {
				display: flex;
				flex-direction: column;
				align-items: center;
			}
		
			.summary-item .label {
				font-size: 24rpx;
				color: #999;
				margin-bottom: 6rpx;
			}
		
			.summary-item .num {
				font-size: 36rpx;
				font-weight: bold;
				color: #333;
			}
		
			.amount-font {
				color: #b1141d;
				font-family: 'DIN', sans-serif;
			}
		
			.line {
				width: 1px;
				height: 60rpx;
				background-color: #eee;
			}
		
			.list-container {
				padding: 0 30rpx;
				margin-top: 90rpx;
			}
		
			.plan-card {
				background-color: #ffffff;
				border-radius: 20rpx;
				margin-bottom: 24rpx;
				padding: 0 30rpx;
				box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.03);
				position: relative;
				overflow: hidden;
			}
		
			.card-done .card-body,
			.card-done .card-header {
				opacity: 0.6;
			}
		
			.card-header {
				display: flex;
				justify-content: space-between;
				align-items: flex-start;
				padding: 24rpx 0;
			}
		
			.header-left {
				display: flex;
				flex-direction: column;
			}
		
			.period-title {
				display: flex;
				align-items: center;
				margin-bottom: 6rpx;
			}
		
			.period-title .icon {
				font-size: 32rpx;
				margin-right: 12rpx;
			}
		
			.period-title .text {
				font-size: 30rpx;
				font-weight: bold;
				color: #333;
			}
		
			.contract-text {
				font-size: 22rpx;
				color: #999;
				padding-left: 44rpx;
			}
		
			.status-tag {
				font-size: 22rpx;
				padding: 6rpx 16rpx;
				border-radius: 8rpx;
				flex-shrink: 0;
			}
		
			.tag-red {
				color: #b1141d;
				background-color: #fdf2f2;
			}
		
			.tag-gray {
				color: #999;
				background-color: #f0f0f0;
			}
		
			.divider {
				height: 1px;
				background-color: #f5f5f5;
			}
		
			.card-body {
				padding: 24rpx 0 10rpx; /* 底部padding调小，留给按钮区域 */
			}
		
			.main-row {
				display: flex;
				justify-content: space-between;
				align-items: flex-end;
				margin-bottom: 20rpx;
			}
		
			.info-block {
				display: flex;
				flex-direction: column;
			}
		
			.align-right {
				align-items: flex-end;
			}
		
			.label {
				font-size: 24rpx;
				color: #999;
				margin-bottom: 8rpx;
			}
		
			.big-amount {
				font-size: 44rpx;
				font-weight: bold;
				color: #333;
			}
		
			.plan-card:not(.card-done) .big-amount {
				color: #b1141d;
			}
		
			.date-text {
				font-size: 28rpx;
				color: #666;
				font-weight: 500;
			}
		
			.sub-row {
				display: flex;
				align-items: center;
				font-size: 24rpx;
				color: #999;
			}
		
			.sub-divider {
				margin: 0 12rpx;
				color: #ddd;
			}
		
			.warning-row {
				margin-top: 20rpx;
				background-color: #fdf2f2;
				padding: 12rpx 20rpx;
				border-radius: 8rpx;
				display: flex;
				align-items: center;
			}
		
			.warn-icon {
				font-size: 24rpx;
				margin-right: 10rpx;
			}
		
			.warn-text {
				font-size: 24rpx;
				color: #b1141d;
				font-weight: bold;
			}
		
			/* 新增：底部操作栏 */
			.card-action {
				border-top: 1px dashed #eee;
				padding: 24rpx 0;
				margin-top: 20rpx;
				display: flex;
				justify-content: flex-end;
			}
		
			.repay-btn {
				background-color: #b1141d;
				color: #fff;
				width: 600rpx;
				font-size: 26rpx;
				padding: 0 40rpx;
				height: 64rpx;
				line-height: 64rpx;
				border-radius: 32rpx;
				margin: 0 auto;
				box-shadow: 0 6rpx 12rpx rgba(177, 20, 29, 0.2);
			}
		
			.repay-btn:active {
				opacity: 0.9;
			}
		
			.stamp {
				position: absolute;
				right: 20rpx;
				top: 80rpx;
				width: 140rpx;
				height: 140rpx;
				border: 4rpx solid #ddd;
				border-radius: 50%;
				display: flex;
				align-items: center;
				justify-content: center;
				transform: rotate(-25deg);
				color: #ddd;
				font-weight: bold;
				font-size: 32rpx;
				opacity: 0.5;
				pointer-events: none;
			}
		
			.no-more {
				text-align: center;
				font-size: 24rpx;
				color: #ccc;
				padding: 20rpx 0;
			}
		
			.empty-state {
				display: flex;
				flex-direction: column;
				align-items: center;
				margin-top: 150rpx;
				color: #999;
			}
		
			.empty-icon {
				font-size: 80rpx;
				margin-bottom: 20rpx;
				opacity: 0.5;
			}
</style>