<template>
	<view class="content">
	<view class="up">
		<view class="iconfont icon-fanhui" @click="goToMain()"></view>
		<text>用户隐私政策条款</text>
	</view>
	<view class="down">
		<view @click="goToRegisterContent()">
			<text>用户注册协议</text>
		</view>
		<view @click="goToPrivateContent()">
			<text>隐私政策</text>
		</view>
		<view @click="goToPermission()">
			<text>权限管理</text>
		</view>
	</view>
	</view>
</template>

<script>
	export default{
		// 页面加载时自动调用创建权限接口
		onLoad() {
			this.createPermission();
		},
		methods:{
			// 调用后端接口创建权限（后端会判断是否存在，不存在才创建）
			async createPermission() {
				const username = getApp().globalData.username;
				if (username) {
					try {
						await uni.$http.post('/permission/create_permission', { 
							username: username 
						});
					} catch (e) {
						console.log("权限初始化请求失败", e);
					}
				}
			},
			goToMain(){
				uni.switchTab({
					url:"/pages/main/main"
				})
			},
			goToRegisterContent(){
				uni.navigateTo({
					url:"/pages/tables/registerContent"
				})
			},
			goToPrivateContent(){
				uni.navigateTo({
					url:"/pages/tables/privateContent"
				})
			},
			goToPermission(){
				uni.navigateTo({
					url:"/pages/tables/permission"
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
		background-color: #b1141d; /* 统一的导航栏主题色 */
		
		box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
		margin-bottom: 45rpx;
		
		.iconfont{
			font-size: 45rpx;
			padding-left: 30rpx; 
			color: #ffffff; /* 图标改为白色 */
		}
		
		text{
			display: block;
			position: absolute;
			font-size: 35rpx;
			left: 250rpx;
			color: #ffffff; /* 标题文字改为白色 */
			font-weight: bold;
		}
	}
	
	.down{
		view{
			width: 700rpx;
			box-sizing: border-box;
			background-color: #ffffff;
			height: 100rpx;
			
			border-radius: 15rpx;
			
			margin: 0 25rpx 25rpx 25rpx;
			padding-left: 30rpx;
			
			box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
			
			line-height: 100rpx;
			
			text{
				font-size: 34rpx;
				color: #333333;
			}
		}
	}
</style>