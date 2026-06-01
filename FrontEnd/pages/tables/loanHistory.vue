<template>
	<view class="content">
		<view class="up">
		    	<view class="iconfont icon-fanhui" @click="goToLoan()"></view>
		    	<text>贷款记录</text>
		</view>
		<view class="down">
			<view class="list-container">
			      
			      <block v-if="loanList.length > 0">
			        <view class="record-card" v-for="(item, index) in loanList" :key="item.id">
			          
			          <view class="card-header">
			            <view class="name-wrap">
			              <text class="product-name">贷款人:{{ item.name }}</text>
			            </view>
			            <view class="status-tag" :class="getStatusClass(item.applyStatus)">
			              {{ item.applyStatus }}o
			            </view>
			          </view>
			          
			          <view class="divider"></view>
			
			          <view class="card-body">
			            <view class="data-row">
			              <view class="data-item">
			                <text class="label">申请金额</text>
			                <text class="value amount">¥ {{ formatMoney(item.applyQuota) }}</text>
			              </view>
			              <view class="data-item">
			                <text class="label">申请期限</text>
			                <text class="value">{{ item.applyPeriod }}</text>
			              </view>
			            </view>
			
			            <view class="data-row mt-20">
			              <view class="data-item">
			                <text class="label">还款方式</text>
			                <text class="value small">{{ item.repayMethod }}</text>
			              </view>
			              <view class="data-item">
			                <text class="label">申请时间</text>
			                <text class="value small time">{{ item.applyTime }}</text>
			              </view>
			            </view>
			          </view>
			
			          <view class="card-footer" v-if="item.applyStatus === '已拒绝'">
			            <text class="reason-label">拒绝原因：</text>
			            <text class="reason-text">{{ item.refuseReason }}</text>
			          </view>
			
			        </view>
			        
			        <view class="no-more">- 到底了 -</view>
			      </block>
			
			      <block v-else-if="!loading">
			        <view class="empty-state">
			          <text class="empty-icon">📭</text>
			          <text class="empty-text">暂无贷款记录</text>
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
	      loanList: []
	    };
	  },
	  
	  onLoad() {
	    this.getLoanRecords();
	  },
	  
	  methods: {
		goToLoan(){
			uni.navigateBack({
								delta:1
							})
		},
		  
	    async getLoanRecords() {
	      uni.showLoading({ title: '加载中...' });
	      
	      // setTimeout(() => {
	        // const mockResponse = [
	        //   {
	        //     "id": 23,
	        //     "userId": 2345,
	        //     "productId": 234456,
	        //     "name": "大旺仔",
	        //     "applyQuato": "300000",
	        //     "applyPeriod": "12个月",
	        //     "applyTime": "2025-09-08 09:05:37",
	        //     "applyStatus": "审核中",
	        //     "refuseReason": "未拒绝",
	        //     "repayMethod": "手动还款"
	        //   },
	        //   {
	        //     "id": 24,
	        //     "userId": 2345,
	        //     "productId": 234457,
	        //     "name": "旺仔",
	        //     "applyQuato": "50000",
	        //     "applyPeriod": "6个月",
	        //     "applyTime": "2025-08-01 14:20:00",
	        //     "applyStatus": "已通过",
	        //     "refuseReason": "未拒绝",
	        //     "repayMethod": "自动代扣"
	        //   },
	        //   {
	        //     "id": 25,
	        //     "userId": 2345,
	        //     "productId": 234458,
	        //     "name": "小旺仔",
	        //     "applyQuato": "1000000",
	        //     "applyPeriod": "24个月",
	        //     "applyTime": "2025-07-15 10:10:10",
	        //     "applyStatus": "已拒绝",
	        //     "refuseReason": "综合评分不足",
	        //     "repayMethod": "手动还款"
	        //   }
	        // ];
	
	        // this.loanList = mockResponse;
			let res=await uni.$http.get('/loan/history',{})
			
			if(res.code==200){
				const mockResponse=res.data
				this.loanList=mockResponse
			}
			
	        this.loading = false;
	        uni.hideLoading();
	      // }, 500);
	    },
	
	    formatMoney(numStr) {
	      if (!numStr) return '0.00';
	      return Number(numStr).toLocaleString();
	    },
	
	    getStatusClass(status) {
	      if (status === '已通过') return 'status-success';
	      if (status === '已拒绝') return 'status-fail';
	      return 'status-pending'; 
	    },
	  }
	};
</script>

<style scoped lang="less">
	.content {
				min-height: 100vh;
				background-color: #f7f7f7;
				}
		
		.up{
			box-sizing: border-box;
			padding-top: 75rpx;
			margin-bottom: 20rpx;
			
			position: fixed;
			display: flex;
			align-items: center;
			
			z-index: 3;
			width: 750rpx;
			height: 150rpx;
			background-color: #ffffff;
			
			box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
			
			.iconfont{
				font-size: 45rpx;
			}
			
			text{
				display: block;
				font-size:35rpx;
				margin-left: 270rpx;
			}
		}
		
		.down{
			padding-top: 180rpx;
		}
		
		.list-container {
		  padding: 0 30rpx;
		}
		
		.record-card {
		  background-color: #ffffff;
		  border-radius: 20rpx;
		  padding: 30rpx;
		  margin-bottom: 24rpx;
		  box-shadow: 0 4rpx 16rpx rgba(177, 20, 29, 0.08);
		}
		
		.card-header {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  padding-bottom: 20rpx;
		}
		
		.name-wrap {
		  display: flex;
		  align-items: center;
		}
		
		.product-name {
		  font-size: 32rpx;
		  font-weight: bold;
		  color: #333;
		  margin-left: 10rpx;
		}
		
		.status-tag {
		  font-size: 24rpx;
		  padding: 6rpx 16rpx;
		  border-radius: 8rpx;
		  font-weight: 500;
		}
		
		.status-pending {
		  color: #e6a23c;
		  background-color: #fdf6ec;
		}
		
		.status-success {
		  color: #67c23a;
		  background-color: #f0f9eb;
		}
		
		.status-fail {
		  color: #b1141d;
		  background-color: #fdeced;
		}
		
		.divider {
		  height: 1px;
		  background-color: #f5f5f5;
		  margin-bottom: 20rpx;
		}
		
		.card-body {
		  display: flex;
		  flex-direction: column;
		}
		
		.data-row {
		  display: flex;
		  justify-content: space-between;
		}
		
		.mt-20 {
		  margin-top: 20rpx;
		}
		
		.data-item {
		  display: flex;
		  flex-direction: column;
		}
		
		.data-item:last-child {
		  align-items: flex-end;
		}
		
		.label {
		  font-size: 24rpx;
		  color: #999;
		  margin-bottom: 6rpx;
		}
		
		.value {
		  font-size: 28rpx;
		  color: #333;
		  font-weight: 500;
		}
		
		.amount {
		  font-size: 36rpx;
		  font-weight: bold;
		  color: #b1141d; 
		  font-family: 'DIN', sans-serif;
		}
		
		.small {
		  font-size: 26rpx;
		  color: #666;
		}
		
		.time {
		  font-family: monospace;
		}
		
		.card-footer {
		  margin-top: 24rpx;
		  padding-top: 20rpx;
		  border-top: 1px dashed #eee;
		  font-size: 24rpx;
		}
		
		.reason-label {
		  color: #b1141d;
		  font-weight: bold;
		}
		
		.reason-text {
		  color: #666;
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