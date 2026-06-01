<template>
	<view class="content">
		<view class="up">
		    	<view class="iconfont icon-fanhui" @click="goToLoan()"></view>
		    	<text>逾期记录</text>
		</view>
		<view class="down">
			<view class="list-container">
			      
			      <block v-if="overdueList.length > 0">
			        <view 
			          class="record-card" 
			          v-for="(item, index) in overdueList" 
			          :key="item.id"
			        >
			          <view class="card-header">
			            <view class="time-box">
			              <text class="icon">🕒</text>
			              <text class="label">逾期时间：</text>
			              <text class="value">{{ formatDate(item.overdueTime) }}</text>
			            </view>
			            <view class="status-tag" :class="item.statue ? 'tag-gray' : 'tag-red'">
			              {{ item.statue ? '已结清' : '待处理' }}
			            </view>
			          </view>
			          
			          <view class="divider"></view>
			
			          <view class="card-body">
			            <view class="main-info">
			              <text class="label">产生逾期罚息(元)</text>
			              <text class="amount">¥ {{ formatMoney(item.overdueMoney) }}</text>
			            </view>
			
			            <view class="detail-list">
			              <view class="detail-item">
			                <text class="item-label">借款人姓名</text>
			                <text class="item-value">{{ item.name }}</text>
			              </view>
			              
			              <view class="detail-item">
			                <text class="item-label">记录编号</text>
			                <text class="item-value">OD-{{ item.id }}</text>
			              </view>
			
			              <view class="detail-item warning-item">
			                <text class="item-label">信用分影响</text>
			                <view class="score-decrease">
			                  <text>信用分扣除 {{ item.creditDecrease }} 分</text>
			                </view>
			              </view>
			            </view>
			          </view>
			
			          <view class="card-footer" v-if="!item.statue">
			            <button class="pay-btn" @click="handleRepay(item.id)">立即还款</button>
			          </view>
			
			        </view>
			        <view class="no-more">- 暂无更多记录 -</view>
			      </block>
			
			      <block v-else-if="!loading">
			        <view class="empty-state">
			          <text class="empty-icon">🛡️</text>
			          <text class="empty-text">太棒了，您没有逾期记录</text>
			          <text class="empty-sub">保持良好的信用习惯</text>
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
	      overdueList: []
	    };
	  },
	  onLoad() {
	    this.getOverdueData();
	  },
	  methods: {
		goToLoan(){
			uni.navigateBack({
								delta:1
							})
		},
		  
	    async getOverdueData() {
	      uni.showLoading({ title: '加载中...' });
	      
	      // setTimeout(() => {
	        // const mockData = [
	        //   {
	        //     "id": 12,
	        //     "name": "张三",
	        //     "overdueTime": "2025-09-08 09:05:37",
	        //     "statue": false, 
	        //     "overdueMoney": 200,
	        //     "creditDecrease": 5
	        //   },
	        //   {
	        //     "id": 13,
	        //     "name": "张三",
	        //     "overdueTime": "2025-09-30 09:05:37",
	        //     "statue": false,
	        //     "overdueMoney": 500,
	        //     "creditDecrease": 15,
	        //   },
	        //   {
	        //     "id": 11,
	        //     "name": "张三",
	        //     "overdueTime": "2025-01-15 10:00:00",
	        //     "statue": true, 
	        //     "overdueMoney": 50,
	        //     "creditDecrease": 2
	        //   }
	        // ];
			
			let res=await uni.$http.get('/repay/overdue',{})
			
			if(res.code==200){
				const mockData=res.data
				this.overdueList=mockData
			}
	
	        // this.overdueList = mockData;
	        this.loading = false;
	        uni.hideLoading();
	      // }, 500);
	    },
	
	    formatDate(dateStr) {
	      if (!dateStr) return '--';
	      return dateStr.split(' ')[0];
	    },
	
	    formatMoney(num) {
	      return Number(num).toLocaleString();
	    },
	
	    handleRepay(id) {
	      uni.showToast({
	        title: '正在跳转支付...',
	        icon: 'none'
	      });
	    }
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
		  margin-bottom: 24rpx;
		  padding: 0 30rpx;
		  box-shadow: 0 4rpx 16rpx rgba(177, 20, 29, 0.08); /* 红色系阴影 */
		  overflow: hidden;
		}
		
		/* 卡片头部 */
		.card-header {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  padding: 24rpx 0;
		}
		
		.time-box {
		  display: flex;
		  align-items: center;
		  font-size: 26rpx;
		  color: #666;
		}
		
		.time-box .icon {
		  margin-right: 8rpx;
		  font-size: 30rpx;
		}
		
		.time-box .value {
		  color: #333;
		  font-weight: 500;
		  margin-left: 8rpx;
		}
		
		.status-tag {
		  font-size: 22rpx;
		  padding: 6rpx 16rpx;
		  border-radius: 8rpx;
		}
		
		.tag-red {
		  color: #b1141d;
		  background-color: #fdf2f2;
		  border: 1px solid rgba(177, 20, 29, 0.1);
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
		  padding: 30rpx 0;
		}
		
		.main-info {
		  display: flex;
		  flex-direction: column;
		  align-items: center;
		  margin-bottom: 40rpx;
		}
		
		.main-info .label {
		  font-size: 24rpx;
		  color: #999;
		  margin-bottom: 10rpx;
		}
		
		.main-info .amount {
		  font-size: 56rpx;
		  font-weight: bold;
		  color: #b1141d;
		  font-family: 'DIN', sans-serif;
		}
		
		.detail-item {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  margin-bottom: 20rpx;
		  font-size: 28rpx;
		}
		
		.item-label {
		  color: #666;
		}
		
		.item-value {
		  color: #333;
		  font-weight: 500;
		}
		
		.warning-item {
		  background-color: #fffaf0;
		  padding: 16rpx 20rpx;
		  border-radius: 12rpx;
		  margin-top: 10rpx;
		}
		
		.score-decrease {
		  display: flex;
		  align-items: center;
		  color: #ff9c00; 
		  font-weight: bold;
		}
		
		.score-decrease .iconfont {
		  margin-right: 8rpx;
		}
		
		.card-footer {
		  border-top: 1px dashed #eee;
		  padding: 24rpx 0;
		}
		
		.pay-btn {
		  background-color: #b1141d;
		  color: #fff;
		  font-size: 30rpx;
		  height: 80rpx;
		  line-height: 80rpx;
		  border-radius: 40rpx;
		  box-shadow: 0 6rpx 16rpx rgba(177, 20, 29, 0.2);
		}
		
		.pay-btn:active {
		  background-color: #961119;
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
		  color: #b1141d;
		}
		
		.empty-sub {
		  font-size: 24rpx;
		  color: #ccc;
		  margin-top: 10rpx;
		}
</style>