<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToMain()"></view>
			<text>还款记录</text>
		</view>
		<view class="down">
			<view class="record-list">
			      <view class="card" v-for="(item, index) in recordList" :key="item.id">
			        <view class="card-top">
			          <view class="title-wrap">
			            <text class="item-name">{{ item.name }}</text>
			            <text class="item-id">计划ID: {{ item.palnId }}</text>
			          </view>
			          <view class="status-badge">已还款</view>
			        </view>
			        <view class="divider"></view>
			        <view class="card-bottom">
			          <view class="info-row">
			            <text class="label">支付方式</text>
			            <view class="method-wrap">
			              <text class="iconfont method-icon" 
			                :class="getPaymentClass(item.repayMethod)">
			                {{ getPaymentIcon(item.repayMethod) }}
			              </text>
			              <text class="value">{{ item.repayMethod }}</text>
			            </view>
			          </view>
			          <view class="info-row">
			            <text class="label">交易时间</text>
			            <text class="value time">{{ item.repayTime }}</text>
			          </view>
			          <view class="info-row">
			            <text class="label">交易单号</text>
			            <text class="value">{{ item.id }}</text>
			          </view>
			        </view>
			      </view>
			      <view v-if="recordList.length === 0 && !loading" class="empty-state">
			        <text class="empty-icon">📭</text>
			        <text class="empty-text">暂无还款记录</text>
			      </view>
			      <view v-if="recordList.length > 0" class="footer-tip">
			        - 仅展示最近半年的记录 -
			      </view>
			    </view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				loading: true,
				recordList: []
			};
		},
		
		onLoad() {
		    this.getRepayData();
		  },
		
		methods:{
			goToMain(){
				uni.navigateBack({
									delta:1
								})
			},
			
			async getRepayData() {
			      this.loading = true;
			      // setTimeout(() => {
			        // const mockResponse = [
			        //   {
			        //     "id": 123456789,
			        //     "palnId": 234,
			        //     "name": "第12期 个人消费贷还款",
			        //     "repayTime": "2025-09-08 09:05:37",
			        //     "repayMethod": "微信"
			        //   },
			        //   {
			        //     "id": 123456780,
			        //     "palnId": 233,
			        //     "name": "第11期 个人消费贷还款",
			        //     "repayTime": "2025-08-08 14:20:11",
			        //     "repayMethod": "支付宝"
			        //   },
			        //   {
			        //     "id": 123456781,
			        //     "palnId": 232,
			        //     "name": "第10期 个人消费贷还款",
			        //     "repayTime": "2025-07-08 10:00:05",
			        //     "repayMethod": "银行卡"
			        //   }
			        // ];
			
			        // this.recordList = mockResponse;
					let res=await uni.$http.get('/repay/history')
					
					if(res.code==200){
						 const mockResponse=res.data
						this.recordList=mockResponse
					}
					
			        this.loading = false;
			        
					// 真实对接时请使用:
					        /*
					        uni.request({
					          url: '您的接口地址',
					          method: 'GET',
					          success: (res) => {
					             this.recordList = res.data.data; // 根据实际结构调整
					          }
					        })
					        */
			      // }, 500);
			    },
			
			    getPaymentClass(method) {
			      if (method && method.includes('微信')) return 'wx-color';
			      if (method && method.includes('支付宝')) return 'ali-color';
			      return 'card-color';
			    },
			
			    getPaymentIcon(method) {
			      if (method && method.includes('微信')) return '💬';
			      if (method && method.includes('支付宝')) return '支';
			      return '💳';
			    }
		}
	}
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
	
	.record-list {
	  padding: 0 30rpx;
	}
	
	.card {
	  background-color: #ffffff;
	  border-radius: 16rpx;
	  padding: 30rpx;
	  margin-bottom: 24rpx;
	  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}
	
	.card-top {
	  display: flex;
	  justify-content: space-between;
	  align-items: flex-start;
	  margin-bottom: 20rpx;
	}
	
	.title-wrap {
	  display: flex;
	  flex-direction: column;
	}
	
	.item-name {
	  font-size: 32rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 8rpx;
	}
	
	.item-id {
	  font-size: 22rpx;
	  color: #999;
	  background-color: #f0f2f5;
	  padding: 4rpx 12rpx;
	  border-radius: 8rpx;
	  align-self: flex-start;
	}
	
	.status-badge {
	  font-size: 24rpx;
	  color: #4cd964;
	  background-color: rgba(76, 217, 100, 0.1);
	  padding: 6rpx 16rpx;
	  border-radius: 24rpx;
	  font-weight: 500;
	}
	
	.divider {
	  height: 1px;
	  background-color: #f5f5f5;
	  margin-bottom: 20rpx;
	}
	
	.info-row {
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	  margin-bottom: 16rpx;
	  font-size: 26rpx;
	}
	
	.info-row:last-child {
	  margin-bottom: 0;
	}
	
	.label {
	  color: #888;
	}
	
	.value {
	  color: #333;
	  font-weight: 500;
	}
	
	.time {
	  font-family: monospace;
	  color: #666;
	}
	
	.method-wrap {
	  display: flex;
	  align-items: center;
	}
	
	.method-icon {
	  margin-right: 10rpx;
	  font-size: 28rpx;
	  font-weight: bold;
	}
	
	.wx-color { color: #07c160; }
	.ali-color { color: #1677ff; }
	.card-color { color: #ff9c00; }
	
	.empty-state {
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  justify-content: center;
	  padding-top: 100rpx;
	}
	
	.empty-icon {
	  font-size: 80rpx;
	  margin-bottom: 20rpx;
	}
	
	.empty-text {
	  font-size: 28rpx;
	  color: #999;
	}
	
	.footer-tip {
	  text-align: center;
	  font-size: 22rpx;
	  color: #ccc;
	  margin-top: 30rpx;
	}
</style>