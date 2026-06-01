<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToMain()"></view>
			<text>常见问题</text>
		</view>
		<view class="down">
		<view class="faq-list">
		
		      <view class="section">
		        <view class="section-title">一、申请与审核</view>
		        
		        <view class="qa-item">
		          <view class="question">Q：申请贷款需要满足什么条件？</view>
		          <view class="answer">
		            您需要年满 18 周岁，具备完全民事行为能力，且非在校学生。同时，您需要提供真实有效的二代身份证、银行卡，并配合完成人脸识别和相关资质信息的填写。
		          </view>
		        </view>
		
		        <view class="qa-item">
		          <view class="question">Q：提交申请后多久出结果？</view>
		          <view class="answer">
		            我们的系统采用智能风控模型进行自动审核，通常在提交申请后的 5-10 分钟内出具审批结果。若遇到特殊情况进入人工复核流程，可能需要 1-2 个工作日，请您耐心等待。
		          </view>
		        </view>
		      </view>
		
		      <view class="section">
		        <view class="section-title">二、还款与费用</view>
		        
		        <view class="qa-item">
		          <view class="question">Q：如果不按时还款会有什么后果？</view>
		          <view class="answer">
		            逾期还款将产生罚息（违约金），具体费率请以借款合同为准。同时，您的逾期记录将被上报至央行征信中心，严重影响您的个人信用，且平台将保留通过法律途径追偿的权利。
		          </view>
		        </view>
		
		        <view class="qa-item">
		          <view class="question">Q：可以提前还款吗？</view>
		          <view class="answer">
		            可以。您可以随时发起提前结清申请。根据产品类型的不同，提前还款可能会减免剩余期限的利息，具体以实际试算结果为准。
		          </view>
		        </view>
		      </view>
		
		      <view class="section">
		        <view class="section-title">三、账户与安全</view>
		        
		        <view class="qa-item">
		          <view class="question">Q：为什么要进行人脸识别？</view>
		          <view class="answer">
		            为了保障您的账户和资金安全，防止他人冒用您的身份进行欺诈借款，我们在注册和签约环节必须进行人脸活体检测，以确保是您本人操作。
		          </view>
		        </view>
		      </view>
		      
		      <view class="footer-tip">
		        如有其他疑问，请联系人工客服：400-888-8888
		      </view>
		      
		      <view class="footer-padding"></view>
		
		    </view>
			</view>
	</view>
</template>

<script>
	export default{
		methods:{
			goToMain(){
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
	
	.faq-list {
	  padding: 30rpx;
	}
	
	.section {
	  margin-bottom: 40rpx;
	}
	
	.section-title {
	  font-size: 32rpx;
	  font-weight: bold;
	  color: #b1141d;
	  margin-bottom: 24rpx;
	  padding-left: 10rpx;
	  position: relative;
	}
	
	.qa-item {
	  background-color: #ffffff;
	  border-radius: 16rpx;
	  padding: 30rpx;
	  margin-bottom: 24rpx;
	  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.03);
	}
	
	.question {
	  font-size: 30rpx;
	  font-weight: 600;
	  color: #333333;
	  margin-bottom: 16rpx;
	  line-height: 1.4;
	}
	
	.answer {
	  font-size: 28rpx;
	  color: #666666;
	  line-height: 1.6;
	  text-align: justify;
	}
	
	.footer-tip {
	  text-align: center;
	  font-size: 26rpx;
	  color: #999999;
	  margin-top: 40rpx;
	  padding-bottom: 20rpx;
	}
	
	.footer-padding {
	  height: 40rpx;
	}
</style>