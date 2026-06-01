<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToMain()"></view>
			<text>申请进度</text>
		</view>
		<view class="down">
			<view class="list-container">
			      
			      <block v-if="applyList.length > 0">
			        <view class="loan-card" v-for="(item, index) in applyList" :key="index">
			          
			          <view class="card-main">
			            <view class="info-section">
			              <view class="avatar-box">
			                <text class="user-text">{{ item.name.substring(0, 1) }}</text>
			              </view>
			              <view class="text-info">
			                <text class="loan-type">产品贷款</text>
			                <text class="applicant">申请人: {{ item.name }}</text>
			                <text class="date">申请时间: {{ item.date || '2025-11-20' }}</text>
			              </view>
			            </view>
			
			            <view class="status-section">
			              <view class="status-badge" :class="getStatusClass(item.applyStatus)">
			                <text class="iconfont icon">{{ getStatusIcon(item.applyStatus) }}</text>
			                <text class="status-text">{{ item.applyStatus }}</text>
			              </view>
			            </view>
			          </view>
			          
			          <view class="card-bar" :style="{ backgroundColor: getStatusColor(item.applyStatus) }"></view>
			        </view>
			      </block>
			
			      <block v-else-if="!loading">
			        <view class="empty-state">
			          <text class="empty-icon">📭</text>
			          <text class="empty-text">暂无申请记录</text>
			        </view>
			      </block>
			    </view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
		    return {
		      loading: true,
		      applyList: []
		    };
		  },
		  
		onLoad() {
		    this.getProgressData();
		  },
		
		methods:{
			goToMain(){
				uni.navigateBack({
									delta:1
								})
			},
			
			async getProgressData() {
			      uni.showLoading({ title: '加载中...' });
			      
			      // setTimeout(() => {
			        // const mockData = [
			        //   {
			        //     "name": "张三",
			        //     "applyStatus": "审核中",
			        //     "date": "2025-11-28"
			        //   },
			        //   {
			        //     "name": "张三",
			        //     "applyStatus": "已通过",
			        //     "date": "2025-10-15"
			        //   },
			        //   {
			        //     "name": "张三",
			        //     "applyStatus": "已拒绝",
			        //     "date": "2025-09-01"
			        //   }
			        // ];
					
					let res=await uni.$http.get('/other/status')
					
					if(res.code==200){
						const mockData=res.data
						this.applyList=mockData
					}
			
			        // this.applyList = mockData;
			        this.loading = false;
			        uni.hideLoading();
			      // }, 500);
			    },
				
			 getStatusClass(status) {
			      if (status === '已通过') return 'status-success';
			      if (status === '已拒绝') return 'status-fail';
			      return 'status-pending';
			    },
			
			getStatusIcon(status) {
			      if (status === '已通过') return '✔';
			      if (status === '已拒绝') return '✕';
			      return '⟳';
			    },
				
			getStatusColor(status) {
			      if (status === '已通过') return '#4cd964';
			      if (status === '已拒绝') return '#b1141d';
			      return '#f0ad4e';
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
	
	.loan-card {
	  background-color: #ffffff;
	  border-radius: 20rpx;
	  margin-bottom: 30rpx;
	  box-shadow: 0 8rpx 20rpx rgba(177, 20, 29, 0.08);
	  overflow: hidden;
	  position: relative;
	}
	
	.card-main {
	  padding: 30rpx;
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	}
	
	.info-section {
	  display: flex;
	  align-items: center;
	}
	
	.avatar-box {
	  width: 90rpx;
	  height: 90rpx;
	  background-color: #f8f8f8;
	  border-radius: 50%;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  margin-right: 24rpx;
	  border: 2rpx solid #eee;
	}
	
	.user-text {
	  font-size: 36rpx;
	  color: #333;
	  font-weight: bold;
	}
	
	.text-info {
	  display: flex;
	  flex-direction: column;
	}
	
	.loan-type {
	  font-size: 30rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 6rpx;
	}
	
	.applicant {
	  font-size: 24rpx;
	  color: #666;
	  margin-bottom: 4rpx;
	}
	
	.date {
	  font-size: 22rpx;
	  color: #999;
	}
	
	.status-section {
	  display: flex;
	  flex-direction: column;
	  align-items: flex-end;
	}
	
	.status-badge {
	  display: flex;
	  align-items: center;
	  padding: 10rpx 20rpx;
	  border-radius: 30rpx;
	}
	
	.icon {
	  font-size: 28rpx;
	  margin-right: 8rpx;
	  font-weight: bold;
	}
	
	.status-text {
	  font-size: 26rpx;
	  font-weight: bold;
	}
	
	.status-pending {
	  background-color: #fff7e6;
	  color: #f0ad4e;
	}
	
	.status-success {
	  background-color: #eaffea;
	  color: #4cd964;
	}
	
	.status-fail {
	  background-color: #fdeced;
	  color: #b1141d;
	}
	
	.card-bar {
	  height: 6rpx;
	  width: 100%;
	  opacity: 0.8;
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