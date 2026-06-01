<template>
	<view class="content">
	<view class="up">
			<view class="iconfont icon-fanhui" @click="goToPrivate()"></view>
			<text>隐私政策</text>
	</view>
	<view class="container">
	    <view class="header">
	      <text class="title">--App 隐私政策</text>
	      <text class="subtitle">版本生效日期：2025年12月32日</text>
	    </view>
	
	    <scroll-view scroll-y="true" class="content-scroll">
	      <view class="article">
	        
	        <view class="section">
	          <view class="para">
	            --非常重视用户的隐私和个人信息保护。您在使用我们的--App产品与服务时，我们可能会收集和使用您的相关信息。
	          </view>
	          <view class="para">
	            本《隐私政策》将向您说明我们在您使用服务时如何收集、使用、保存、共享和转让这些信息，以及我们为您提供的访问、更新、删除和保护这些信息的方式。
	          </view>
	          <view class="para bold-red">
	            本政策与您所使用的服务息息相关，请您在使用我们的服务前仔细阅读并确认您已经充分理解本政策所写明的内容。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">一、我们如何收集和使用您的个人信息</view>
	          <view class="para">为了向您提供网贷风控及借贷相关服务，我们需要收集您在使用服务过程中产生的或您主动提供的以下信息：</view>
	          
	          <view class="para">
	            <text class="bold">1. 注册与账户信息：</text>
	            当您注册账号时，我们需要收集您的<text class="bold">手机号码</text>及登录密码，以便为您创建账户并发送短信验证码进行身份验证。
	          </view>
	
	          <view class="para">
	            <text class="bold">2. 实名认证与身份识别信息：</text>
	            根据金融监管及反洗钱要求，您需要提供<text class="bold">姓名、身份证号、身份证正反面照片</text>。同时，我们需要使用<text class="bold">人脸识别技术</text>收集您的面部特征信息，以验证是否为您本人操作，防止身份冒用。
	          </view>
	
	          <view class="para">
	            <text class="bold">3. 借贷资质审核信息：</text>
	            为了评估您的信用状况（风控建模）及还款能力，我们需要您提供<text class="bold">学历信息、职业信息、月收入情况、居住地址</text>以及<text class="bold">紧急联系人信息</text>。
	          </view>
	
	          <view class="para">
	            <text class="bold">4. 银行卡与支付信息：</text>
	            在您申请放款或进行还款时，我们需要收集您的<text class="bold">银行卡号、开户行信息及预留手机号</text>，以便完成资金的划扣与结算。
	          </view>
	
	          <view class="para">
	            <text class="bold">5. 设备与日志信息（用于风控反欺诈）：</text>
	            为了保障账户安全、进行反欺诈监测及信用评分，我们会通过自动化手段收集您的<text class="bold">设备信息（包括IMEI、IMSI、MAC地址、Android ID、设备型号、操作系统版本）</text>、IP地址、网络类型以及<text class="bold">地理位置信息（GPS/Wi-Fi/基站信息）</text>。
	          </view>
	        </view>
	
	        <!-- 第二条 -->
	        <view class="section">
	          <view class="section-title">二、我们如何共享、转让、公开披露您的个人信息</view>
	          
	          <view class="para">
	            <text class="bold">1. 接入征信机构：</text>
	            <text class="bold-red">您同意并授权我们向中国人民银行征信中心、百行征信等合法设立的征信机构查询、报送您的信用信息（包括但不限于借款记录、逾期记录）。</text>
	          </view>
	
	          <view class="para">
	            <text class="bold">2. 资金合作方：</text>
	            为完成放款，我们可能需要将您的姓名、证件号码、银行卡信息共享给提供资金的持牌金融机构（如银行、信托公司）。
	          </view>
	
	          <view class="para">
	            <text class="bold">3. 第三方支付与服务：</text>
	            在您使用还款功能时，您的订单支付相关信息会被共享给第三方支付机构（如支付宝、微信支付）。
	          </view>
	        </view>
	
	        <!-- 第三条 -->
	        <view class="section">
	          <view class="section-title">三、我们如何保护您的个人信息</view>
	          <view class="para">
	            1. 我们已采取符合业界标准的安全防护措施（如SSL加密传输、AES数据存储加密、访问控制）保护您提供的个人信息。
	          </view>
	          <view class="para">
	            2. 我们会建立专门的管理制度、流程和组织以保障信息的安全。例如，我们严格限制访问信息的人员范围，要求他们遵守保密义务，并进行审计。
	          </view>
	        </view>
	
	        <!-- 第四条 -->
	        <view class="section">
	          <view class="section-title">四、您的权利</view>
	          <view class="para">
	            您有权访问、更正您的个人信息。在结清所有应付款项且无未决争议的情况下，您可以申请注销账户。账户注销后，我们将停止为您提供产品或服务，并依据法律法规的要求对您的个人信息进行删除或匿名化处理（但法律法规规定需保留的交易记录等除外）。
	          </view>
	        </view>
	
	        <!-- 第五条 -->
	        <view class="section">
	          <view class="section-title">五、政策更新</view>
	          <view class="para">
	            我们可能会适时对本隐私政策进行修订。当条款发生重大变更时，我们会在App内通过弹窗或公告等方式通知您。
	          </view>
	        </view>
	
	        <!-- 底部说明 -->
	        <view class="footer-note">
	           如有任何疑问、意见或建议，请通过App内的“联系客服”功能与我们联系。
	        </view>
	        
	        <view class="footer-padding"></view>
	      </view>
	    </scroll-view>
	  </view>
	</view>
</template>

<script>
	export default{
			methods:{
				goToPrivate(){
					uni.redirectTo({
						url:"/pages/tables/privates"
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
		
		position: relative;
		display: flex;
		align-items: center;
		
		width: 750rpx;
		height: 150rpx;
		background-color: #ffffff;
		
		box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
		margin-bottom: 20rpx;
		
		.iconfont{
			font-size: 45rpx;
		}
		
		text{
			display: block;
			position: absolute;
			font-size:35rpx;
			left: 320rpx;
		}
	}
	
	.container {
	  display: flex;
	  flex-direction: column;
	  height: 100vh;
	  background-color: #f8f9fa;
	}
	
	.header {
	  padding: 40rpx 30rpx 20rpx;
	  background-color: #ffffff;
	  border-bottom: 1px solid #eeeeee;
	}
	
	.title {
	  font-size: 36rpx;
	  font-weight: bold;
	  color: #333333;
	  display: block;
	  text-align: center;
	  margin-bottom: 10rpx;
	}
	
	.subtitle {
	  font-size: 24rpx;
	  color: #999999;
	  display: block;
	  text-align: center;
	}
	
	.content-scroll {
	  flex: 1;
	  padding: 0;
	  overflow-y: hidden; 
	}
	
	.article {
	  padding: 30rpx 30rpx;
	}
	
	.section {
	  margin-bottom: 40rpx;
	}
	
	.section-title {
	  font-size: 30rpx;
	  font-weight: bold;
	  color: #333;
	  margin-bottom: 20rpx;
	  padding-left: 16rpx;
	  border-left: 8rpx solid #b1141d;
	}
	
	.para {
	  font-size: 28rpx;
	  color: #555;
	  line-height: 1.6;
	  margin-bottom: 16rpx;
	  text-align: justify;
	}
	
	.bold {
	  font-weight: bold;
	  color: #333;
	}
	
	.bold-red {
	  font-weight: bold;
	  color: #e64340;
	  border: 1px dashed #e64340;
	  padding: 10rpx;
	  border-radius: 8rpx;
	  background-color: #fff5f5;
	  margin-top: 10rpx;
	  display: block;
	}
	
	.footer-note {
	  font-size: 24rpx;
	  color: #999;
	  text-align: center;
	  margin-top: 40rpx;
	  padding-bottom: 20rpx;
	}
	
	.footer-padding {
	  height: 40rpx;
	}
</style>