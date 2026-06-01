<template>
	<view>
		<view class="up">
			<view class="iconfont icon-fanhui" @click="comeBack()"></view>
			<text class="text-left">已有账号，</text>
			<navigator url="/pages/tables/login" open-type="navigate"><text class="text-right">点击登录</text></navigator>
		</view>
		
		<view class="middle">
			<view><text class="text-up-up">你好!</text></view>
			<view><text class="text-up">请注册</text></view>
			<view>
				<input type="text" focus placeholder="请输入用户昵称" v-model="username"/>
				<input type="text" placeholder="请输入用户密码" password="true" v-model="password"/>
			</view>
			<view>
				<lCheckbox icon="circle" checkedColor="#fa4d37" iconSize="28rpx" @click="hasClick()"><text class="all">我已阅读并同意<text class="left">《用户注册协议》</text>和<text class="right">《隐私政策》</text></text></lCheckbox>
			</view>
		</view>
		
		<view class="bottom">
			<button type="warn" :disabled="canClick" @click="register()">点击注册</button>
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
				password:''
			};
		},
		methods:{
			hasClick(){
				this.clicked=!this.clicked
			},
			comeBack(){
				uni.navigateBack({delta:1})
			},
			
			async register(){
					let res=await uni.$http.post('/users/register',{
						name:this.username,
						password:this.password
					})
					if(res.code==200){
						uni.redirectTo({
							url:"/pages/tables/login"
						})
					}
			}
		},
		computed:{
			canClick(){
				if(this.username.length>0&&this.password.length>0&&this.clicked){
					return !this.active
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
		display: flex;
		align-items: center;
		padding: 20rpx;
		padding-top: 90rpx;
		font-size: 28rpx;
		
		.iconfont{
			font-size: 33rpx;
		}
		
		.text-left{
			padding-left: 400rpx;
		}
		
		navigator {
			.text-right{
				color: #fa4d37;
			}
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