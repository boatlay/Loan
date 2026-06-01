<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToMain()"></view>
			<text>关于App</text>
		</view>
		<view class="container">
		    <view class="brand-section">
		      <view class="logo-box">
		        <text class="logo-text">贷</text>
		      </view>
		      <text class="app-name">放心用App</text>
		      <text class="app-version">Version 1.0.0 (Build 202511)</text>
		    </view>
		
		    <view class="info-card">
		      <view class="card-title">产品介绍</view>
		      <view class="card-content">
		        本产品是由信软学院学生自主研发的互联网个人网贷风控金融系统。我们致力于利用大数据与机器学习技术，为您提供精准的风控审核、高效的贷款审批以及安全的资金管理服务，解决传统线下审核慢、手续繁琐的痛点。
		      </view>
		    </view>
		
		    <view class="info-card">
		      <view class="card-title">核心功能</view>
		      <view class="list-item">• 智能风控审核：多维度数据评估，自动化决策。</view>
		      <view class="list-item">• 在线贷款申请：全流程线上操作，极速放款。</view>
		      <view class="list-item">• 电子合同签署：合法合规，保障双方权益。</view>
		      <view class="list-item">• 实时进度查询：申请状态全程透明可追溯。</view>
		    </view>
		
		    <view class="info-card">
		      <view class="card-title">研发团队 (第七组)</view>
		      <view class="team-row">
		        <text class="role">项目经理：</text>
		        <text class="name">张哲</text>
		      </view>
		      <view class="team-row">
		        <text class="role">业务分析：</text>
		        <text class="name">杨子坤</text>
		      </view>
		      <view class="team-row">
		        <text class="role">技术预研：</text>
		        <text class="name">周星航</text>
		      </view>
		      <view class="team-row">
		        <text class="role">测试工程：</text>
		        <text class="name">李可</text>
		      </view>
		    </view>
		
		    <view class="info-card">
		      <view class="card-title">联系我们</view>
		      <view class="contact-row">
		        <text class="label">客服热线：</text>
		        <text class="value">888-888-8888</text>
		      </view>
		      <view class="contact-row">
		        <text class="label">官方邮箱：</text>
		        <text class="value">uestc@uestcxinruan.com</text>
		      </view>
		      <view class="contact-row">
		        <text class="label">公司地址：</text>
		        <text class="value">信软学院</text>
		      </view>
		    </view>
		
		    <view class="footer">
		      <text class="copyright">Copyright © 2025 信软 All Rights Reserved.</text>
		      <text class="tech-support">基于 SpringBoot + Vue + uniApp架构开发</text>
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
	
	.container {
	  min-height: 100vh;
	  background-color: #f8f9fa;
	  padding-bottom: 60rpx;
	  padding-top: 170rpx;
	}
	
	.brand-section {
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  padding: 80rpx 0 60rpx;
	  background-color: #ffffff;
	  border-bottom: 1px solid #eee;
	  margin-bottom: 20rpx;
	}
	
	.logo-box {
	  width: 140rpx;
	  height: 140rpx;
	  background: linear-gradient(to right, #b1141d 0%, #ff7e5f 100%);
	  border-radius: 24rpx;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  margin-bottom: 24rpx;
	  box-shadow: 0 5px 15px rgba(255, 126, 95, 0.5);
	}
	
	.logo-text {
	  color: #ffffff;
	  font-size: 60rpx;
	  font-weight: bold;
	}
	
	.app-name {
	  font-size: 34rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 8rpx;
	}
	
	.app-version {
	  font-size: 24rpx;
	  color: #999;
	}
	
	.info-card {
	  background-color: #ffffff;
	  margin: 0 30rpx 24rpx;
	  padding: 30rpx;
	  border-radius: 12rpx;
	}
	
	.card-title {
	  font-size: 30rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 20rpx;
	  border-left: 6rpx solid #b1141d;
	  padding-left: 16rpx;
	}
	
	.card-content {
	  font-size: 26rpx;
	  color: #666;
	  line-height: 1.6;
	  text-align: justify;
	}
	
	.list-item {
	  font-size: 26rpx;
	  color: #555;
	  line-height: 1.8;
	  margin-bottom: 8rpx;
	}
	
	.team-row {
	  display: flex;
	  justify-content: flex-start;
	  margin-bottom: 12rpx;
	  font-size: 26rpx;
	}
	
	.team-row .role {
	  color: #888;
	  width: 160rpx;
	}
	
	.team-row .name {
	  color: #333;
	  font-weight: 500;
	}
	
	.contact-row {
	  display: flex;
	  margin-bottom: 12rpx;
	  font-size: 26rpx;
	}
	
	.contact-row .label {
	  color: #888;
	  width: 160rpx;
	}
	
	.contact-row .value {
	  color: #333;
	  flex: 1;
	}
	
	.footer {
	  margin-top: 40rpx;
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	}
	
	.copyright {
	  font-size: 22rpx;
	  color: #ccc;
	  margin-bottom: 8rpx;
	}
	
	.tech-support {
	  font-size: 20rpx;
	  color: #e0e0e0;
	}
</style>