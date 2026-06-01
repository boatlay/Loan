<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToLoan()"></view>
			<text>还款帮助</text>
		</view>
		<view class="down">
			<view class="help-content">
			
			      <view class="card">
			        <view class="card-header">
			          <text class="icon">①</text>
			          <text class="card-title">何时需要还款？</text>
			        </view>
			        <view class="card-body">
			          <view class="paragraph">
			            <text class="highlight">还款日：</text>具体的还款日期以您签署的《借款合同》及 App 首页“我的账单”中显示的日期为准。通常为每月的固定日期（如每月 10 号）。
			          </view>
			          <view class="paragraph">
			            <text class="highlight">扣款时间：</text>系统通常会在还款日当天的 <text class="bold">10:00 - 18:00</text> 期间发起自动扣款。为了避免因银行转账延迟导致逾期，建议您<text class="bold">提前一天</text>将足额资金存入绑定的还款银行卡中。
			          </view>
			        </view>
			      </view>
			
			      <view class="card">
			        <view class="card-header">
			          <text class="icon">②</text>
			          <text class="card-title">如何进行还款？</text>
			        </view>
			        <view class="card-body">
			          <view class="step-item">
			            <text class="step-num">方式一</text>
			            <view class="step-content">
			              <text class="step-title">自动代扣（推荐）</text>
			              <text class="step-desc">只需保证您的绑定银行卡内余额充足，系统会在到期日自动划扣应还金额，无需手动操作。</text>
			            </view>
			          </view>
			          
			          <view class="step-item">
			            <text class="step-num">方式二</text>
			            <view class="step-content">
			              <text class="step-title">主动还款</text>
			              <text class="step-desc">登录 App，点击“首页”或“个人中心” -> “我的借款” -> “立即还款”，支持使用银行卡、微信支付或支付宝进行支付。</text>
			            </view>
			          </view>
			        </view>
			      </view>
			
			      <view class="card warning-card">
			        <view class="card-header">
			          <text class="icon">③️</text>
			          <text class="card-title">逾期会有什么后果？</text>
			        </view>
			        <view class="card-body">
			          <view class="warning-item">
			            <text class="dot">•</text>
			            <text class="text">产生罚息：逾期后将按日计收违约金（罚息），具体费率请查看合同约定，时间越长费用越高。</text>
			          </view>
			          <view class="warning-item">
			            <text class="dot">•</text>
			            <text class="text">影响征信：您的逾期记录将被上报至<text class="bold">中国人民银行征信中心</text>，不良信用记录将影响您未来的房贷、车贷及信用卡申请。</text>
			          </view>
			          <view class="warning-item">
			            <text class="dot">•</text>
			            <text class="text">催收介入：平台及委托方将通过短信、电话或信函等方式提醒您履约。</text>
			          </view>
			        </view>
			      </view>
			
			      <view class="footer-note">
			        珍惜信用，按时还款。如遇还款困难，请及时联系客服。
			      </view>
			      
			      <view class="footer-padding"></view>
			
			    </view>
		</view>
	</view>
</template>

<script>
	export default{
		methods:{
			goToLoan(){
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
	
	.help-content {
	  padding: 30rpx;
	}
	
	.card {
	  background-color: #ffffff;
	  border-radius: 16rpx;
	  padding: 30rpx;
	  margin-bottom: 30rpx;
	  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.03);
	}
	
	.card-header {
	  display: flex;
	  align-items: center;
	  margin-bottom: 24rpx;
	  border-bottom: 1px dashed #eee;
	  padding-bottom: 20rpx;
	}
	
	.icon {
	  font-size: 36rpx;
	  margin-right: 16rpx;
	}
	
	.card-title {
	  font-size: 32rpx;
	  font-weight: bold;
	  color: #333;
	}
	
	.card-body {
	  font-size: 28rpx;
	  color: #666;
	  line-height: 1.6;
	}
	
	.paragraph {
	  margin-bottom: 16rpx;
	  text-align: justify;
	}
	
	.highlight {
	  color: #b1141d;
	  font-weight: bold;
	}
	
	.bold {
	  font-weight: bold;
	  color: #333;
	}
	
	.step-item {
	  display: flex;
	  margin-bottom: 24rpx;
	}
	
	.step-item:last-child {
	  margin-bottom: 0;
	}
	
	.step-num {
	  background-color: #bbbbbb;
	  color: #b1141d;
	  font-size: 22rpx;
	  padding: 4rpx 12rpx;
	  border-radius: 8rpx;
	  height: 40rpx;
	  line-height: 32rpx;
	  margin-right: 20rpx;
	  flex-shrink: 0;
	  display: block;
	}
	
	.step-content {
	  flex: 1;
	}
	
	.step-title {
	  display: block;
	  font-size: 28rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 6rpx;
	}
	
	.step-desc {
	  font-size: 26rpx;
	  color: #888;
	  line-height: 1.5;
	  text-align: justify;
	}
	
	.warning-card {
	  border: 1px solid #fff0f0;
	}
	
	.warning-card .card-header {
	  border-bottom-color: #fff0f0;
	}
	
	.warning-card .card-title {
	  color: #e64340;
	}
	
	.warning-item {
	  display: flex;
	  margin-bottom: 12rpx;
	}
	
	.warning-item .dot {
	  color: #e64340;
	  margin-right: 12rpx;
	  font-weight: bold;
	}
	
	.warning-item .text {
	  color: #555;
	  text-align: justify;
	}
	
	.footer-note {
	  text-align: center;
	  font-size: 24rpx;
	  color: #bbb;
	  margin-top: 40rpx;
	}
	
	.footer-padding {
	  height: 40rpx;
	}
</style>