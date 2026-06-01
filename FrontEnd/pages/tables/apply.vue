<template>
	<view class="content">
		<view class="up">
		    	<view class="iconfont icon-fanhui" @click="goToProduct()"></view>
		    	<text>填写贷款申请表</text>
		</view>
		<view class="down">
			<view class="form-container">
			      
			      <view class="form-item">
			        <text class="label required">用户姓名</text>
			        <input class="input" type="text" v-model="formData.name" placeholder="请输入真实姓名" />
			      </view>
			
			      <view class="form-item">
			        <text class="label required">申请额度</text>
			        <view class="input-wrap">
			          <input class="input" type="number" v-model.number="formData.applyQuota" placeholder="请输入整数金额" />
			          <text class="unit">元</text>
			        </view>
			      </view>
			
			      <view class="form-item">
			        <text class="label required">申请期限</text>
			        <view class="input-wrap">
			          <input class="input" type="number" v-model.number="formData.applyPeriod" placeholder="请输入期限" />
			          <text class="unit">个月</text>
			        </view>
			      </view>
			
			      <view class="form-item">
			        <text class="label required">还款方式</text>
			        <picker 
			          mode="selector" 
			          :range="repayOptions" 
			          @change="onRepayMethodChange"
			        >
			          <view :class="formData.repayMethod ? 'picker-value' : 'picker-placeholder'">
			            {{ formData.repayMethod || '请选择还款方式' }}
			          </view>
			        </picker>
			      </view>
			
			    </view>
			
			    <view class="footer-btn">
			      <button class="submit-btn" @click="submitForm">立即申请</button>
			    </view>
		</view>
	</view>
</template>

<script>
	export default {
	  data() {
	    return {
	      userId: '',
	      productId: '',
	      
	      applyId: null,
	
	      formData: {
	        name: '',
	        applyQuota: null,
	        applyPeriod: null,
	        repayMethod: '' ,
	      },
	
	      repayOptions: ['自动扣款', '手动扣款']
	    };
	  },
	  onLoad(options) {
	    if (options.userId) {
	      this.userId = options.userId;
	    }
	    if (options.productId) {
	      this.productId = options.productId;
	    }
	    
	    console.log(`页面加载完成 - UserId: ${this.userId}, ProductId: ${this.productId}`);
	  },
	  methods: {
		goToProduct(){
			uni.redirectTo({
				url:"/pages/product/product"
			})
		},
		  
	    onRepayMethodChange(e) {
	      const index = e.detail.value;
	      this.formData.repayMethod = this.repayOptions[index];
	    },
	
	    async submitForm() {
	      if (!this.formData.name) return uni.showToast({ title: '请输入姓名', icon: 'none' });
	      if (!this.formData.applyQuota) return uni.showToast({ title: '请输入申请额度', icon: 'none' });
	      if (!this.formData.applyPeriod) return uni.showToast({ title: '请输入申请期限', icon: 'none' });
	      if (!this.formData.repayMethod) return uni.showToast({ title: '请选择还款方式', icon: 'none' });
	
	      uni.showLoading({ title: '提交申请中...' });
	      // setTimeout(() => {
	//         const res = {
	//           data: 1234 ,
	//         };
	
			let res=await uni.$http.post('/loan/apply',{
				name:this.formData.name,
				userId:this.userId,
				productId:this.productId,
				applyQuota:this.formData.applyQuota,
				applyPeriod:this.formData.applyPeriod,
				repayMethod:this.formData.repayMethod
			})
			
			uni.hideLoading();
			
			if(res.code==200){
				this.applyId = res.data;
				uni.showToast({ title: '申请提交成功', icon: 'success' });
			}
	        
	        
	        // 也可以存入本地缓存，视业务需求而定
	        // uni.setStorageSync('currentApplyId', this.applyId);
	
	        // console.log('提交成功，获取到的 ApplyId:', this.applyId);
	        // console.log('提交的完整数据:', {
	        //   userId: this.userId,
	        //   productId: this.productId,
	        //   ...this.formData
	        // });
	        
	
	        setTimeout(() => {
	          // uni.reLaunch({ url: '/pages/index/index' });
			  uni.redirectTo({url:`/pages/tables/contract?userId=${this.userId}&applyId=${this.applyId}`})
	        }, 1500);
	
	      // }, 1000);
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
				margin-left: 225rpx;
			}
		}
		
		.down{
			padding-top: 180rpx;
		}
		
		.form-container {
		  margin: 0 30rpx 40rpx;
		  background-color: #fff;
		  border-radius: 20rpx;
		  padding: 0 30rpx;
		  box-shadow: 0 4rpx 16rpx rgba(177, 20, 29, 0.08);
		}
		
		.form-item {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		  padding: 36rpx 0;
		  border-bottom: 1px solid #f5f5f5;
		}
		
		.form-item:last-child {
		  border-bottom: none;
		}
		
		.label {
		  font-size: 30rpx;
		  color: #333;
		  width: 200rpx;
		}
		
		.required::before {
		  content: '*';
		  color: #b1141d;
		  margin-right: 4rpx;
		}
		
		.input {
		  flex: 1;
		  text-align: right;
		  font-size: 30rpx;
		  color: #333;
		}
		
		.input-wrap {
		  flex: 1;
		  display: flex;
		  align-items: center;
		  justify-content: flex-end;
		}
		
		.unit {
		  font-size: 30rpx;
		  color: #333;
		  margin-left: 10rpx;
		  font-weight: 500;
		}
		
		.picker-value {
		  font-size: 30rpx;
		  color: #333;
		}
		
		.picker-placeholder {
		  font-size: 30rpx;
		  color: #ccc;
		}
		
		.arrow {
		  color: #ccc;
		  margin-left: 10rpx;
		  font-size: 28rpx;
		}
		
		.footer-btn {
		  padding: 0 40rpx;
		  margin-top: 60rpx;
		}
		
		.submit-btn {
		  background-color: #b1141d;
		  color: #fff;
		  border-radius: 44rpx;
		  font-size: 32rpx;
		  height: 90rpx;
		  line-height: 90rpx;
		  box-shadow: 0 8rpx 20rpx rgba(177, 20, 29, 0.25);
		  transition: opacity 0.2s;
		}
		
		.submit-btn:active {
		  opacity: 0.9;
		}
</style>