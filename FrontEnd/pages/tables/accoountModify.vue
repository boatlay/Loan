<template>
	<view>
		<view class="up">
			<view class="iconfont icon-fanhui" @click="comeToMain()"></view>
		</view>
		
		<view class="middle">
			<view><text class="text-up-up">你好!</text></view>
			<view><text class="text-up">可以修改你的账号资料</text></view>
			<view>
				<input type="text" focus placeholder="请输入新的用户昵称" v-model="username"/>
				<input type="text" placeholder="请输入新的用户密码" password="true" v-model="password1"/>
				<input type="text" placeholder="请确认新的用户密码" password="true" v-model="password2"/>
			</view>
			<view>
				<lCheckbox icon="circle" checkedColor="#fa4d37" iconSize="28rpx" @click="hasClick()"><text class="all">我已阅读并同意<text class="left">《用户注册协议》</text>和<text class="right">《隐私政策》</text></text></lCheckbox>
			</view>
		</view>
		
		<view class="bottom">
			<button type="warn" :disabled="canClick" @click="modify()">立即修改</button>
		</view>
	</view>
</template>

<script>
	import LCheckboxGroup from "../../uni_modules/lime-checkbox/components/l-checkbox-group/l-checkbox-group.vue"
	import lCheckbox from "../../uni_modules/lime-checkbox/components/l-checkbox/l-checkbox.vue"
	
	export default{
		data(){
			return{
				active:true,
				clicked:false,
				username:'',
				password1:'',
				password2:''
			};
		},
		methods:{
			hasClick(){
				this.clicked=!this.clicked
			},
			comeToMain(){
				uni.navigateBack({
									delta:1
								})
			},
			
			async modify() {
							let res = await uni.$http.put('/users/modify', {
								name: this.username,
								password: this.password1
							})
							
							if(res.code == 200) {
								// 🌟 核心修改：接收后端返回的新 Token
								const newToken = res.data;
								
								// 1. 覆盖全局变量中的旧数据
								getApp().globalData.username = this.username;
								getApp().globalData.token = newToken;
								
								// 2. 覆盖本地缓存中的旧数据，确保下次打开 App 还是登录状态
								uni.setStorageSync('username', this.username);
								uni.setStorageSync('token', newToken);
								
								// 顺便加个用户体验优化：给个成功的提示
								uni.showToast({
									title: '资料修改成功',
									icon: 'success',
									duration: 1500
								});
								
								// 延迟一下跳转，让用户能看清成功的提示框
								setTimeout(() => {
									uni.switchTab({
										url: "/pages/main/main"
									});
								}, 1000);
							}
							}
						},
		computed:{
			canClick(){
				if(this.username.length>0&&this.password1.length>0&&this.clicked&&this.password2.length>0){
					if(this.password1===this.password2){
						return !this.active
					}
				}
				return this.active
			}
		},
		components:{
			LCheckboxGroup,
			lCheckbox
		}
	}
</script>

<style lang="less" scoped>
	.up{
		width: 750rpx;
		padding: 20rpx;
		padding-top: 80rpx;
		
		.iconfont{
			font-size: 33rpx;
		}
	}
	
	.middle{
		font-size: 28rpx;
		width: 750rpx;
		margin: 50rpx 20rpx;
		
		.text-up-up{
			font-size: 45rpx;
		}
		
		.text-up{
			display: block;
			font-size: 45rpx;
			margin-bottom: 30rpx;
		}
		
		input{
			background-color: #e8e8e8;
			border-radius: 10rpx;
			width: 690rpx;
			height: 90rpx;
			padding-left: 20rpx;
			margin-bottom: 20rpx;
			font-size: 40rpx;
		}
		
		.all{
			font-size: 30rpx;
		}
		
		.left{
			font-size: 28rpx;
			color: #fa4d37;
		}
		
		.right{
			font-size: 28rpx;
			color:  #fa4d37;
		}
	}
	
	.bottom{
		margin:90rpx 20rpx 0 20rpx;
		
		button{
			font-weight: bold;
			color: #e7e7e7;
			font-size: 40rpx;
		}
	}
</style>