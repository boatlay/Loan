<template>
	<view class="content">
		<view class="up">
			<view class="iconfont icon-fanhui" @click="goToPrivates()"></view>
			<text>权限管理</text>
		</view>
		
		<view class="list">
			<view class="item">
				<text>通讯录权限</text>
				<switch :checked="contactChecked" @change="e => contactChecked = e.detail.value" color="#fa4d37" />
			</view>
			
			<view class="item">
				<text>地理位置权限</text>
				<switch :checked="locationChecked" @change="e => locationChecked = e.detail.value" color="#fa4d37" />
			</view>
			
			<view class="item">
				<text>相机权限</text>
				<switch :checked="cameraChecked" @change="e => cameraChecked = e.detail.value" color="#fa4d37" />
			</view>
			
			<view class="item">
				<text>爬虫权限</text>
				<switch :checked="weiboChecked" @change="handleWeiboChange" color="#fa4d37" />
			</view>
			
			<view class="item input-item" v-if="weiboChecked">
				<text>你的昵称：</text>
				<input class="weibo-input" type="text" placeholder="请输入您的昵称" v-model="weiboUrl" />
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				permissionData: null, 
				
				contactChecked: false,
				locationChecked: false,
				cameraChecked: false,
				weiboChecked: false,
				weiboUrl: ''
			}
		},
		async onLoad() {
			await this.getPermission();
		},
		
		// 🌟 1. 拦截安卓的物理返回键或屏幕边缘左滑手势
		onBackPress(options) {
			// 如果是咱们下面代码里 setTimeout 中调用的 uni.navigateBack，则直接放行，不拦截
			if (options.from === 'navigateBack') {
				return false;
			}
			
			// 拦截系统默认返回，走咱们的延时安全返回逻辑
			this.handleSafeBack();
			
			// 返回 true 代表阻止系统原本的瞬间退出行为
			return true; 
		},
		
		methods: {
			// 🌟 2. 左上角自定义返回图标的点击事件
			goToPrivates() {
				this.handleSafeBack();
			},
			
			// 🌟 3. 统一的“延时安全返回”逻辑
			handleSafeBack() {
				// 开启带 mask（遮罩防误触）的 Loading 提示
				uni.showLoading({ 
					title: '保存中...', 
					mask: true 
				});
				
				// 触发网络请求（不要加 await 死等，让它在后台默默发出去即可）
				this.updatePermission();
				
				// 强行等待 1 秒（1000毫秒），确保请求完全飞到 Spring Boot 后端
				setTimeout(() => {
					uni.hideLoading();
					uni.navigateBack({ delta: 1 }); // 1秒后真正退出页面
				}, 1500);
			},
			
			// 1. 获取权限数据
				// 1. 获取权限数据
							async getPermission() {
								const username = getApp().globalData.username;
								if (!username) return;
								
								try {
									// 先创建，确保表里一定有该用户
									await uni.$http.post('/permission/create_permission', { username: username });
									
									// 再查询
									let res = await uni.$http.get('/permission/get_permission', { 
										username: username 
									});
									
									// 🌟 核心修改：你的 request.js 已经解构好了，直接判断 res 即可！
									console.log("后端返回的完整JSON：", res);
									
									if (res.code === 200 && res.data) {
										this.permissionData = res.data; // 把真实的权限实体存起来
										
										this.contactChecked = this.permissionData.contactPermission === 1;
										this.locationChecked = this.permissionData.locationPermission === 1;
										this.cameraChecked = this.permissionData.cameraPermission === 1;
										
										this.weiboUrl = this.permissionData.weiboUrl || '';
										this.weiboChecked = this.weiboUrl.trim().length > 0;
									} else {
									    console.error("查无此权限数据或 code 不为 200", res);
									}
								} catch(e) {
									console.error("获取权限失败", e);
								}
							},
			
			handleWeiboChange(e) {
				this.weiboChecked = e.detail.value;
				if (!this.weiboChecked) {
					this.weiboUrl = '';
				}
			},
			
			// 真正去发请求的方法（删除了原本不可靠的 onUnload）
			async updatePermission() {
							// 如果没有拿到底层数据，说明还没获取到 id，不能更新
							if (!this.permissionData || !this.permissionData.id) {
								console.error("未获取到权限数据的记录 ID，取消更新");
								return;
							}
							
							// 🌟 明确地一个一个字段拼装，绝不依赖展开运算符
							let updatedData = {
								id: this.permissionData.id,              // 必须有主键 JPA 才能 UPDATE
								username: this.permissionData.username,  // 明确带上用户名
								contactPermission: this.contactChecked ? 1 : 0,
								locationPermission: this.locationChecked ? 1 : 0,
								cameraPermission: this.cameraChecked ? 1 : 0,
								weiboUrl: this.weiboChecked ? this.weiboUrl : ''
							};
			
							console.log("最终发送给后端的数据：", updatedData);
			
							try {
								await uni.$http.put('/permission/update_permission', updatedData);
							} catch (e) {
								console.error("保存权限状态失败", e);
							}
						}
		}
	}
</script>

<style scoped lang="less">
	.content {
		min-height: 100vh;
		background-color: #f7f7f7;
	}
	
	/* 保持与客服页面一样的导航栏样式 */
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
		
		.iconfont {
			font-size: 45rpx;
			padding-left: 30rpx; 
			color: #ffffff; /* 图标改为白色 */
		}
		
		text {
			display: block;
			position: absolute;
			font-size: 35rpx;
			left: 305rpx;
			color: #ffffff; /* 标题文字改为白色 */
			font-weight: bold;
		}
	}
	
	/* 列表菜单样式 */
	.list {
		.item {
			width: 700rpx;
			box-sizing: border-box;
			background-color: #ffffff;
			min-height: 100rpx;
			border-radius: 15rpx;
			margin: 0 25rpx 25rpx 25rpx;
			padding: 0 30rpx;
			box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
			
			display: flex;
			align-items: center;
			justify-content: space-between; /* 左右分布 */
			
			text {
				font-size: 34rpx;
				color: #333333;
			}
		}
		
		/* 专门给微博输入框定制的样式 */
		.input-item {
			display: flex;
			flex-direction: row;
			justify-content: flex-start; /* 靠左对齐 */
			
			.weibo-input {
				flex: 1; /* 占据剩余空间 */
				font-size: 32rpx;
				background-color: #f9f9f9;
				height: 70rpx;
				padding-left: 20rpx;
				border-radius: 10rpx;
			}
		}
	}
</style>