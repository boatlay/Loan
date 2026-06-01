<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToIndex()"></view>
			<text>贷款流程</text>
		</view>
		<view class="down">
			<view class="process-list">
			
			      <view class="step-item">
			        <view class="step-left">
			          <view class="circle active">1</view>
			          <view class="line"></view>
			        </view>
			        <view class="step-right">
			          <text class="step-title">注册与实名认证</text>
			          <text class="step-desc">使用手机号完成注册，上传本人二代身份证并进行人脸识别，确保身份真实有效。</text>
			        </view>
			      </view>
			
			      <view class="step-item">
			        <view class="step-left">
			          <view class="circle active">2</view>
			          <view class="line"></view>
			        </view>
			        <view class="step-right">
			          <text class="step-title">填写资质信息</text>
			          <text class="step-desc">完善个人基本信息（如学历、职业、居住地等），并授权查询相关信用数据，以便系统评估您的授信额度。</text>
			        </view>
			      </view>
			
			      <view class="step-item">
			        <view class="step-left">
			          <view class="circle active">3</view>
			          <view class="line"></view>
			        </view>
			        <view class="step-right">
			          <text class="step-title">智能风控审核</text>
			          <text class="step-desc">提交申请后，系统将自动进行风险评估。审核通常在 10 分钟内完成，部分情况可能需要人工电话核实。</text>
			        </view>
			      </view>
			
			      <view class="step-item">
			        <view class="step-left">
			          <view class="circle active">4</view>
			          <view class="line"></view>
			        </view>
			        <view class="step-right">
			          <text class="step-title">确认借款与签约</text>
			          <text class="step-desc">审核通过后，获得授信额度。您需确认借款金额、期限及还款计划，并在线签署电子借款合同。</text>
			        </view>
			      </view>
			
			      <view class="step-item">
			        <view class="step-left">
			          <view class="circle finish">5</view>
			        </view>
			        <view class="step-right">
			          <text class="step-title">放款到账</text>
			          <text class="step-desc">签约成功后，资金将实时打入您绑定的银行卡中。具体到账时间视银行处理速度而定。</text>
			        </view>
			      </view>
			
			    </view>
			
			    <view class="footer-tip">
			      <text class="icon">💡</text>
			      <text>温馨提示：全程线上操作，无任何隐形费用。</text>
			    </view>
			    
			    <view class="footer-padding"></view>
		</view>
	</view>
</template>

<script>
	export default{
		methods:{
			goToIndex(){
				uni.navigateBack({
					delta:1
				})
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
		z-index: 3;
		
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
		padding-top: 150rpx;
	}
	
	.process-list {
	  padding: 50rpx 40rpx;
	}
	
	.step-item {
	  display: flex;
	  min-height: 160rpx; 
	}
	
	.step-left {
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  margin-right: 30rpx;
	  width: 60rpx; 
	}
	
	.circle {
	  width: 50rpx;
	  height: 50rpx;
	  border-radius: 50%;
	  background-color: #e0e0e0;
	  color: #ffffff;
	  font-size: 28rpx;
	  font-weight: bold;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  z-index: 2; 
	  flex-shrink: 0;
	}
	
	.circle.active {
	  background-color: #b1141d; 
	  box-shadow: 0 5px 15px rgba(255, 126, 95, 0.5);
	}
	
	.circle.finish {
	  background-color: #b1141d; 
	  box-shadow: 0 5px 15px rgba(255, 126, 95, 0.5);
	}
	
	.line {
	  width: 4rpx;
	  flex: 1;
	  background-color: #f0f0f0;
	  margin: 10rpx 0;
	}
	
	.step-right {
	  flex: 1;
	  padding-bottom: 50rpx;
	}
	
	.step-title {
	  font-size: 32rpx;
	  font-weight: bold;
	  color: #333333;
	  display: block;
	  margin-bottom: 12rpx;
	  margin-top: 4rpx; 
	}
	
	.step-desc {
	  font-size: 28rpx;
	  color: #666666;
	  line-height: 1.6;
	  text-align: justify;
	}
	
	.footer-tip {
	  margin: 20rpx 40rpx;
	  padding: 24rpx;
	  background-color: #fffbe6;
	  border-radius: 12rpx;
	  display: flex;
	  align-items: flex-start;
	  color: #d48806;
	  font-size: 26rpx;
	}
	
	.footer-tip .icon {
	  margin-right: 12rpx;
	  font-size: 30rpx;
	}
	
	.footer-padding {
	  height: 40rpx;
	}
</style>