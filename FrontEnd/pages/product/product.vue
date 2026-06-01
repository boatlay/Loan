<template>
	<view class="content">
		<view class="up">
		    	<view class="iconfont icon-fanhui" @click="goToLoan()"></view>
		    	<text>贷款产品</text>
		</view>
		<view class="down">
			<view class="product-list">
			      
			      <block v-if="productList.length > 0">
			        <view 
			          class="product-card" 
			          v-for="(item, index) in productList" 
			          :key="item.id"
			          :class="{ 'card-disabled': !item.status }"
			        >
			          
			          <view class="card-header">
			            <view class="name-box">
			              <text class="product-name">{{ item.name }}</text>
			              <text class="hot-tag" v-if="item.status && index === 0">热门推荐</text>
			              <text class="id-tag">ID: {{ item.id }}</text>
			            </view>
			            <view class="dot-menu">···</view>
			          </view>
			
			          <view class="card-body">
			            
			            <view class="quota-section">
			              <text class="quota-label">最高可借额度(元)</text>
			              <text class="quota-value">{{ formatMoney(item.maxQuota) }}</text>
			              <text class="quota-range">额度范围: {{ formatMoney(item.minQuota) }} - {{ formatMoney(item.maxQuota) }}</text>
			            </view>
			
			            <view class="info-grid">
			              <view class="grid-item">
			                <text class="value rate-text">{{ formatRate(item.rate) }}</text>
			                <text class="label">年化利率</text>
			              </view>
			              <view class="line"></view>
			              <view class="grid-item">
			                <text class="value">{{ item.minPeriod }}-{{ item.maxPeriod }}期</text>
			                <text class="label">贷款期限</text>
			              </view>
			              <view class="line"></view>
			              <view class="grid-item">
			                <text class="value">{{ item.repayMethod }}</text>
			                <text class="label">还款方式</text>
			              </view>
			            </view>
			          </view>
			
			          <view class="card-footer">
			            <button 
			              class="apply-btn" 
			              :class="item.status ? 'btn-active' : 'btn-disabled'"
			              @click="handleApply(item)"
			              :disabled="!item.status"
			            >
			              {{ item.status ? '立即申请' : '暂不可用' }}
			            </button>
			          </view>
			
			        </view>
			        <view class="no-more">- 更多产品接入中 -</view>
			      </block>
			
			      <block v-else-if="!loading">
			        <view class="empty-state">
			          <text class="empty-icon">🏪</text>
			          <text class="empty-text">暂时没有上架的贷款产品</text>
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
	      productList: []
	    };
	  },
	  onLoad() {
	    this.getProductData();
	  },
	  methods: {
		goToLoan(){
			uni.navigateBack({
								delta:1
							})
		},
		  
	    async getProductData() {
	      uni.showLoading({ title: '加载中...' });
	      
	//       setTimeout(() => {
	//         const mockData = [
	//           {
	// 				"id": 1001,
	//                 "name": "旺仔优选贷",
	// 				"minQuota": 2000,
	// 				"maxQuota": 20000,
	// 				"rate": 0.08, 
	// 				"repayMethod": "等额本息",
	// 				"minPeriod": 12,
	// 				"maxPeriod": 24,
	// 				"status": true
	// 				},
	// 				{
	// 				"id": 1002,
	// 				"name": "极速小旺仔",
	// 				"minQuota": 200,
	// 				"maxQuota": 2000,
	// 				"rate": 0.12,
	// 				"repayMethod": "随借随还",
	// 				"minPeriod": 3,
	// 				"maxPeriod": 12,
	// 				"status": true
	// 				},
	// 				{
	// 				"id": 1003,
	// 				"name": "大额经营贷",
	// 				"minQuota": 50000,
	// 				"maxQuota": 500000,
	// 				"rate": 0.05,
	// 				"repayMethod": "先息后本",
	// 				"minPeriod": 12,
	// 				"maxPeriod": 36,
	// 				"status": false 
	// 				}
	//         ];
	
	//         this.productList = mockData;
	//         this.loading = false;
			let res=await uni.$http.get('/product',{})
			if(res.code==200){
				const mockData=res.data.content
				this.productList=mockData
			}
			this.loading=false
			uni.hideLoading();
	//       }, 500);
	    },
	
	    formatMoney(num) {
	      if (!num) return '0';
	      return Number(num).toLocaleString();
	    },
	
	    formatRate(rate) {
	      if (!rate) return '0%';
	      return (rate * 100).toFixed(1) + '%';
	    },
	
	    handleApply(item) {
	      if (!item.status) return;
	      
	      uni.showToast({
	        title: `正在申请 ${item.name}...`,
	        icon: 'none'
	      });
		  
	      uni.navigateTo({
	      	url:`/pages/tables/qualification?productId=${item.id}`
	      })
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
			padding-top: 65rpx;
			margin-bottom: 20rpx;
			
			position: fixed;
			display: flex;
			align-items: center;
			
			z-index: 3;
			width: 750rpx;
			height: 140rpx;
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
			padding-top: 170rpx;
		}
		
		.product-list {
		  padding: 0 30rpx;
		}
		
		.product-card {
		  background-color: #ffffff;
		  border-radius: 20rpx;
		  margin-bottom: 30rpx;
		  padding: 30rpx;
		  box-shadow: 0 8rpx 20rpx rgba(177, 20, 29, 0.06);
		  position: relative;
		  overflow: hidden;
		  transition: transform 0.2s;
		}
		
		.product-card:active {
		  transform: scale(0.99);
		}
		
		.card-disabled {
		  opacity: 0.7;
		  background-color: #fafafa;
		}
		.card-disabled .product-name {
		  color: #999;
		}
		
		.card-header {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  margin-bottom: 30rpx;
		}
		
		.name-box {
		  display: flex;
		  align-items: center;
		}
		
		.product-name {
		  font-size: 34rpx;
		  font-weight: bold;
		  color: #333;
		  margin-right: 16rpx;
		}
		
		.hot-tag {
		  font-size: 20rpx;
		  color: #ff9c00;
		  background-color: #fff5e6;
		  padding: 4rpx 12rpx;
		  border-radius: 8rpx;
		  border: 1px solid #ffe4b5;
		}
		
		.id-tag {
		  font-size: 20rpx;
		  color: #ccc;
		  margin-left: 10rpx;
		  font-family: monospace;
		}
		
		.dot-menu {
		  color: #ccc;
		  font-weight: bold;
		  letter-spacing: 2rpx;
		}
		
		.quota-section {
		  display: flex;
		  flex-direction: column;
		  align-items: center;
		  margin-bottom: 40rpx;
		}
		
		.quota-label {
		  font-size: 24rpx;
		  color: #999;
		  margin-bottom: 8rpx;
		}
		
		.quota-value {
		  font-size: 60rpx;
		  font-weight: bold;
		  color: #b1141d;
		  font-family: 'DIN', sans-serif;
		  line-height: 1;
		  margin-bottom: 12rpx;
		}
		
		.quota-range {
		  font-size: 24rpx;
		  color: #666;
		  background-color: #f8f8f8;
		  padding: 6rpx 20rpx;
		  border-radius: 20rpx;
		}
		
		.info-grid {
		  display: flex;
		  justify-content: space-between;
		  align-items: center;
		  margin-bottom: 40rpx;
		  padding: 0 10rpx;
		}
		
		.grid-item {
		  flex: 1;
		  display: flex;
		  flex-direction: column;
		  align-items: center;
		}
		
		.line {
		  width: 1px;
		  height: 40rpx;
		  background-color: #eee;
		}
		
		.value {
		  font-size: 28rpx;
		  font-weight: bold;
		  color: #333;
		  margin-bottom: 6rpx;
		}
		
		.rate-text {
		  color: #ff9c00;
		}
		
		.label {
		  font-size: 22rpx;
		  color: #999;
		}
		
		.card-footer {
		  border-top: 1px dashed #eee;
		  padding-top: 24rpx;
		}
		
		.apply-btn {
		  height: 80rpx;
		  line-height: 80rpx;
		  border-radius: 40rpx;
		  font-size: 30rpx;
		  font-weight: bold;
		}
		
		.btn-active {
		  background: linear-gradient(90deg, #d32f2f, #b1141d);
		  color: #fff;
		  box-shadow: 0 6rpx 16rpx rgba(177, 20, 29, 0.25);
		}
		
		.btn-disabled {
		  background-color: #e0e0e0;
		  color: #999;
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