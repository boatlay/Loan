<template>
	<view class="content">
	<view class="up">
		<view class="iconfont icon-fanhui" @click="goToPrivate()"></view>
		<text>用户注册协议</text>
	</view>
	<view class="container">
	    <view class="header">
	      <text class="title">--App 用户注册协议</text>
	      <text class="subtitle">版本生效日期：2025年12月32日</text>
	    </view>
	
	    <scroll-view scroll-y="true" class="content-scroll">
	      <view class="article">
	        
	        <view class="section">
	          <view class="section-title">重要提示</view>
	          <view class="para">欢迎您使用由--提供的--App软件及相关服务。</view>
	          <view class="para bold-red">
	            在您注册成为本平台用户之前，请您务必审慎阅读、充分理解本协议各条款内容，特别是免除或者限制责任的条款、法律适用和争议解决条款。关于免除或者限制责任的条款，我们将以加粗形式标识，您应重点阅读。
	          </view>
	          <view class="para">
	            如果您未满18周岁，或者不具备完全民事行为能力，请您停止注册和使用本服务。<text class="bold">本平台不向在校学生提供借贷服务。</text>
	          </view>
	          <view class="para">
	            当您点击“同意”、“注册”或实际使用本服务，即视为您已阅读并同意本协议的全部内容。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第一条 账户注册与使用</view>
	          <view class="para">
	            <text class="bold">1.1 资格限制：</text>您确认，在您注册成为本平台用户时，您应当是具备完全民事行为能力的自然人。<text class="bold">若您是未成年人或在校学生，请立即停止注册。</text>
	          </view>
	          <view class="para">
	            <text class="bold">1.2 实名认证：</text>基于金融监管要求，您必须提供真实、准确、最新且完整的个人资料（包括但不限于姓名、身份证号、手机号、银行卡信息、人脸识别信息等）。如因您提供的信息不真实而导致的任何损失，由您自行承担。
	          </view>
	          <view class="para">
	            <text class="bold">1.3 账户安全：</text>您有责任妥善保管您的账户账号、密码及验证码。<text class="bold">任何使用您的账户及密码/验证码登录后进行的操作（包括但不限于申请贷款、签署合同、提现），均视为您本人的行为，由此产生的电子签名法律效力及经济责任由您承担。</text>
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第二条 服务内容与规则</view>
	          <view class="para">
	            <text class="bold">2.1 服务概述：</text>本平台为您提供借贷信息中介服务或助贷服务，包括但不限于借款申请、风险评估、额度授信、还款管理等。
	          </view>
	          <view class="para">
	            <text class="bold">2.2 风控审核：</text><text class="bold">您同意并授权本平台及合作的第三方风控机构（包括但不限于征信机构、数据服务商）收集、处理您的个人信息，用于评估您的信用状况和还款能力。</text> 平台有权根据风控模型（含自动决策系统）独立判断是否向您提供借款及具体额度，平台不承诺100%放款。
	          </view>
	          <view class="para">
	            <text class="bold">2.3 电子合同：</text>您在平台通过点击确认或电子签名方式签署的借款合同、抵押合同等法律文件，与纸质合同具有同等法律效力。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第三条 征信及信息授权（核心条款）</view>
	          <view class="para">
	            <text class="bold">3.1 征信查询授权：</text><text class="bold">您特此不可撤销地授权本平台，在您申请借款、额度审批、贷后管理期间，向中国人民银行征信中心、百行征信及其他合法设立的征信机构查询、打印、保存您的信用报告。</text>
	          </view>
	          <view class="para">
	            <text class="bold">3.2 信息上报授权：</text><text class="bold">您同意，若您在借款期间发生逾期等违约行为，本平台有权将您的违约信息（包括但不限于逾期金额、逾期时长）上报至中国人民银行征信中心及其他征信机构，这将严重影响您的个人信用记录。</text>
	          </view>
	          <view class="para">
	            <text class="bold">3.3 数据采集：</text>为了保障资金安全及进行反欺诈风控，您授权平台在必要范围内收集您的设备信息（如IMEI、MAC地址）、地理位置信息等。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第四条 借款与还款</view>
	          <view class="para">
	            <text class="bold">4.1 借款用途：</text>您承诺借款资金仅用于合同约定的合法消费用途（如装修、旅游、教育等），<text class="bold">严禁将资金用于购房、股市、期货、赌博、洗钱或其他非法用途。</text> 一经发现，平台有权提前收回全部贷款并追究法律责任。
	          </view>
	          <view class="para">
	            <text class="bold">4.2 费用说明：</text>您同意按照借款合同约定支付本金、利息及相关服务费用。具体的利率及费用标准以借款页面展示及签署的合同为准。
	          </view>
	          <view class="para">
	            <text class="bold">4.3 逾期责任：</text>
	            <view class="list-item">• 若您未按时足额还款，<text class="bold">平台有权按照合同约定收取逾期罚息（违约金）。</text></view>
	            <view class="list-item">• <text class="bold">平台有权通过电话、短信、信函、委托第三方催收机构或司法途径向您进行催收。</text></view>
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第五条 用户行为规范</view>
	          <view class="para">您在使用本服务过程中，不得从事以下行为：</view>
	          <view class="list-item">1. 冒用他人身份信息进行注册或借款（骗贷）；</view>
	          <view class="list-item">2. 利用技术手段攻击平台系统，如伪造IP、篡改数据接口；</view>
	          <view class="list-item">3. 利用平台漏洞获取不当利益；</view>
	          <view class="list-item">4. 发布虚假、诈骗、辱骂、违法信息。</view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第六条 免责声明</view>
	          <view class="para bold">
	            6.1 在法律允许的范围内，本平台不对下列情形导致的损失承担责任：
	          </view>
	          <view class="list-item bold">• 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素导致本服务系统障碍不能正常运作。</view>
	          <view class="list-item bold">• 由于黑客攻击、电信部门技术调整或故障、系统维护等原因而造成的服务中断或延迟。</view>
	          <view class="para">
	            6.2 平台不对第三方（如银行、支付通道）的服务质量或延迟承担连带责任，但会尽力协助处理。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第七条 协议的终止</view>
	          <view class="para">7.1 出现以下情况时，平台有权暂时冻结或永久注销您的账户，并要求您立即偿还所有应付款项：</view>
	          <view class="list-item">• 您违反本协议或借款合同约定的；</view>
	          <view class="list-item">• 您提供的个人资料被证实为虚假的；</view>
	          <view class="list-item">• 您的账户存在异常风险（如疑似被盗、洗钱风险）。</view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第八条 隐私保护</view>
	          <view class="para">
	            我们重视您的隐私保护，关于您的个人信息收集、使用、存储及保护规则，请详见单独列示的《[App名称] 隐私政策》。《隐私政策》构成本协议不可分割的一部分。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第九条 法律适用与争议解决</view>
	          <view class="para">
	            9.1 本协议的签订、履行、解释及争议解决均适用<text class="bold">中华人民共和国法律</text>。
	          </view>
	          <view class="para">
	            9.2 若您与本平台发生争议，应友好协商解决；协商不成的，<text class="bold">您同意将争议提交至 [公司所在地] 有管辖权的人民法院诉讼解决</text>（或约定提交仲裁委员会仲裁）。
	          </view>
	        </view>
	
	        <view class="section">
	          <view class="section-title">第十条 其他</view>
	          <view class="para">
	            10.1 本平台有权根据法律法规变化或业务需要修改本协议。修改后的协议将通过App公告或弹窗通知您。如您继续使用服务，视为接受修改。
	          </view>
	          <view class="para">
	            10.2 本协议所有条款的标题仅为阅读方便，本身并无实际涵义，不能作为本协议涵义解释的依据。
	          </view>
	        </view>
	        
	        <view class="footer-note">
	           (请您确认：您已阅读并完全理解上述条款，特别是加粗部分，并自愿承担相关法律后果。)
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
		margin-bottom: 20rpx;
		
		position: relative;
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
			position: absolute;
			font-size:35rpx;
			left: 250rpx;
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
	
	.list-item {
	  font-size: 28rpx;
	  color: #555;
	  line-height: 1.6;
	  margin-bottom: 10rpx;
	  padding-left: 20rpx;
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
	}
	
	.footer-note {
	  font-size: 24rpx;
	  color: #999;
	  text-align: center;
	  margin-top: 40rpx;
	  font-style: italic;
	}
	
	.footer-padding {
	  height: 40rpx;
	}
</style>