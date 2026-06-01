<template>
	<view class="container">
		<view class="header-section">
			<view class="nav-bar">
				<view class="iconfont icon-fanhui back-btn" @click="goToMain()"></view>
				<text class="page-title">个人信息管理</text>
			</view>
			<view class="header-bg-decoration"></view>
		</view>

		<view class="content-wrapper">
			<view class="glass-card animate-fade-up">
				<view class="card-header">
					<view class="tag-line"></view>
					<text>基本身份信息</text>
				</view>

				<view class="form-group">
					<view class="form-item">
						<text class="label">姓名</text>
						<input class="input" focus type="text" v-model="formData.name" placeholder="请输入真实姓名" placeholder-style="color:#BCBCBC"/>
					</view>

					<view class="form-item">
						<text class="label">民族</text>
						<picker class="input" mode="selector" :range="ethnicGroups" @change="onNationalityChange">
							<view :class="formData.nationality ? 'picker-text' : 'picker-placeholder'">
								{{ formData.nationality || '请选择民族' }}
							</view>
						</picker>
					</view>

					<view class="form-item">
						<text class="label">身份证号</text>
						<input class="input" type="idcard" v-model="formData.idCardNumber" placeholder="请输入18位号码" maxlength="18" placeholder-style="color:#BCBCBC"/>
					</view>
				</view>
			</view>

			<view class="glass-card animate-fade-up" style="animation-delay: 0.1s;">
				<view class="card-header">
					<view class="tag-line"></view>
					<text>证件影像上传</text>
				</view>
				
				<view class="upload-grid">
					<view class="upload-box" @click="chooseImage('front')">
						<view v-if="!idCardImages.front" class="upload-placeholder">
							<text class="plus">+</text>
							<text>人像面</text>
						</view>
						<image v-else :src="idCardImages.front" class="preview-img" mode="aspectFill"></image>
					</view>

					<view class="upload-box" @click="chooseImage('back')">
						<view v-if="!idCardImages.back" class="upload-placeholder">
							<text class="plus">+</text>
							<text>国徽面</text>
						</view>
						<image v-else :src="idCardImages.back" class="preview-img" mode="aspectFill"></image>
					</view>
				</view>
				<text class="upload-tips">* 请确保照片清晰，无反光，边缘完整</text>
			</view>

			<view class="glass-card animate-fade-up" style="animation-delay: 0.2s;">
				<view class="card-header">
					<view class="tag-line"></view>
					<text>联系方式</text>
				</view>
				<view class="form-group">
					<view class="form-item">
						<text class="label">电话号码</text>
						<input class="input" type="number" v-model="formData.phoneNumber" placeholder="请输入手机号码" maxlength="11" placeholder-style="color:#BCBCBC"/>
					</view>
					<view class="form-item">
						<text class="label">邮箱</text>
						<input class="input" type="text" v-model="formData.email" placeholder="请输入电子邮箱" placeholder-style="color:#BCBCBC"/>
					</view>
					<view class="form-item no-border">
						<text class="label">居住地址</text>
						<input class="input" type="text" v-model="formData.adress" placeholder="请输入详细居住地址" placeholder-style="color:#BCBCBC"/>
					</view>
				</view>
			</view>

			<view class="glass-card animate-fade-up" style="animation-delay: 0.3s;">
				<view class="card-header">
					<view class="tag-line"></view>
					<text>银行卡信息</text>
				</view>
				<view class="form-group">
					<view class="form-item">
						<text class="label">银行卡号</text>
						<input class="input" type="number" v-model="formData.bankCardId" placeholder="请输入银行卡号" placeholder-style="color:#BCBCBC"/>
					</view>
					<view class="form-item">
						<text class="label">开户行</text>
						<input class="input" type="text" v-model="formData.bankName" placeholder="请输入开户行名称" placeholder-style="color:#BCBCBC"/>
					</view>
					<view class="form-item no-border">
						<text class="label">卡状态</text>
						<picker class="input" mode="selector" :range="bankStatusOptions" range-key="label" @change="onBankStatusChange">
							<view :class="currentBankStatusLabel ? 'picker-text' : 'picker-placeholder'">
								{{ currentBankStatusLabel || '请选择银行卡状态' }}
							</view>
						</picker>
					</view>
				</view>
			</view>

			<view class="submit-section">
				<button class="submit-btn" :disabled="canClick" @click="submitForm">
					提交核验
				</button>
			</view>
		</view>
	</view>
</template>

<script>
	// 此处完全保留你原始的 script 逻辑，无需改动
	export default {
		data() {
			return {
				ethnicGroups: ['汉族', '蒙古族', '回族', '藏族', '维吾尔族', '苗族', '彝族', '壮族', '布依族', '朝鲜族', '满族', '侗族', '瑶族', '白族', '土家族', '哈尼族', '哈萨克族', '傣族', '黎族', '傈僳族', '佤族', '畲族', '高山族', '拉祜族', '水族', '东乡族', '纳西族', '景颇族', '柯尔克孜族', '土族', '达斡尔族', '仫佬族', '羌族', '布朗族', '撒拉族', '毛南族', '仡佬族', '锡伯族', '阿昌族', '普米族', '塔吉克族', '怒族', '乌孜别克族', '俄罗斯族', '鄂温克族', '德昂族', '保安族', '裕固族', '京族', '塔塔尔族', '独龙族', '鄂伦春族', '赫哲族', '门巴族', '珞巴族', '基诺族'],
				bankStatusOptions: [{ label: '正常', value: 'active' }, { label: '已冻结', value: 'frozen' }, { label: '已注销', value: 'cancelled' }],
				formData: { name:'', nationality:'', idCardNumber:'', phoneNumber:'', email:'', adress:'', bankCardId:'', bankName:'', bankCardStatus:'', },
				idCardImages: { front: '', back: '' },
			};
		},
		methods:{
			goToMain(){ uni.navigateBack({ delta:1 }) },
			onNationalityChange(e) { this.formData.nationality = this.ethnicGroups[e.detail.value]; },
			onBankStatusChange(e) { this.formData.bankCardStatus = this.bankStatusOptions[e.detail.value].value; },
			chooseImage(type) {
				uni.chooseImage({
					count: 1, sizeType: ['original', 'compressed'], sourceType: ['album', 'camera'],
					success: (res) => {
						if (type === 'front') { this.idCardImages.front = res.tempFilePaths[0]; }
						else { this.idCardImages.back = res.tempFilePaths[0]; }
					}
				});
			},
			async submitForm() {
				let res=await uni.$http.post('/loan/information', this.formData);
				if(res.code==200){ uni.switchTab({ url:"/pages/main/main" }) }
			}
		},
		computed:{
			canClick(){
				const d = this.formData;
				return !(d.name && d.nationality && d.idCardNumber && d.phoneNumber && d.email && d.adress && d.bankCardId && d.bankName && d.bankCardStatus);
			},
			currentBankStatusLabel() {
				const status = this.bankStatusOptions.find(item => item.value === this.formData.bankCardStatus);
				return status ? status.label : '';
			}
		}
	}
</script>

<style lang="scss" scoped>
.container {
	min-height: 100vh;
	background-color: #f8f9fb;
}

/* 渐变头部 */
.header-section {
	height: 320rpx;
	background: linear-gradient(135deg, #8b0e14 0%, #b1141d 100%);
	position: relative;
	padding-top: var(--status-bar-height);
	
	.nav-bar {
		display: flex;
		align-items: center;
		padding: 120rpx 30rpx;
		color: #fff;
		position: relative;
		z-index: 5;
		
		.back-btn { font-size: 44rpx; margin-right: 20rpx; }
		.page-title { font-size: 36rpx; font-weight: bold; letter-spacing: 2rpx; }
	}
	
	.header-bg-decoration {
		position: absolute; right: -50rpx; top: -50rpx;
		width: 280rpx; height: 280rpx;
		background: rgba(255, 255, 255, 0.1);
		border-radius: 50%;
	}
}

.content-wrapper {
	margin-top: -100rpx;
	padding: 0 30rpx 60rpx;
	position: relative;
	z-index: 10;
}

/* 卡片样式 */
.glass-card {
	background: #ffffff;
	border-radius: 30rpx;
	padding: 40rpx 30rpx;
	margin-bottom: 30rpx;
	box-shadow: 0 10rpx 40rpx rgba(0, 0, 0, 0.04);
	
	.card-header {
		display: flex;
		align-items: center;
		margin-bottom: 35rpx;
		
		.tag-line {
			width: 8rpx; height: 32rpx;
			background: #b1141d;
			border-radius: 4rpx;
			margin-right: 16rpx;
		}
		text { font-size: 32rpx; font-weight: bold; color: #222; }
	}
}

/* 表单项 */
.form-item {
	display: flex;
	align-items: center;
	padding: 25rpx 0;
	border-bottom: 1rpx solid #f2f2f2;
	
	.label {
		width: 160rpx;
		font-size: 28rpx;
		color: #666;
	}
	
	.input {
		flex: 1;
		font-size: 30rpx;
		color: #333;
	}
}
.no-border { border-bottom: none; }

/* 图片上传 */
.upload-grid {
	display: flex;
	justify-content: space-between;
	
	.upload-box {
		width: 48%; height: 200rpx;
		background: #f9fafb;
		border: 2rpx dashed #dcdcdc;
		border-radius: 20rpx;
		display: flex;
		align-items: center; justify-content: center;
		overflow: hidden;
	}
	
	.upload-placeholder {
		display: flex; flex-direction: column; align-items: center;
		color: #999; font-size: 24rpx;
		.plus { font-size: 50rpx; color: #ccc; margin-bottom: 8rpx; }
	}
	
	.preview-img { width: 100%; height: 100%; }
}

.upload-tips {
	display: block; margin-top: 20rpx;
	font-size: 22rpx; color: #b1141d; opacity: 0.6;
}

/* 动效 */
@keyframes fadeUp {
	from { opacity: 0; transform: translateY(20rpx); }
	to { opacity: 1; transform: translateY(0); }
}
.animate-fade-up {
	animation: fadeUp 0.5s ease forwards;
}

/* 提交按钮 */
.submit-btn {
	background: linear-gradient(135deg, #b1141d, #e63946);
	color: #fff;
	height: 100rpx; line-height: 100rpx;
	border-radius: 50rpx;
	font-size: 34rpx; font-weight: bold;
	box-shadow: 0 10rpx 30rpx rgba(177, 20, 29, 0.3);
	margin-top: 40rpx;
	
	&[disabled] {
		background: #ebcccc;
		box-shadow: none;
		color: #fff;
	}
}
</style>