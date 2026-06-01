<template>
	<view class="page-wrapper">
		<view class="detail-content" v-if="email.id">
			
			<view class="mail-header">
				<view class="subject">{{ email.subject }}</view>
				
				<view class="sender-info">
					<view class="avatar" :style="{ backgroundColor: email.avatarColor }">
						{{ email.sender ? email.sender[0] : '' }}
					</view>
					<view class="info-text">
						<view class="name">{{ email.sender }}</view>
						<view class="date">发送于 {{ email.date }}</view>
					</view>
				</view>
			</view>

			<view class="divider"></view>

			<view class="mail-body">
				<rich-text :nodes="email.content"></rich-text>
			</view>
		</view>
		
		<view v-else class="loading-state">
			加载中...
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 初始化为空对象，配合 template 里的 v-if 使用
				email: {} 
			}
		},
		onLoad(options) {
			if (options.data) {
				try {
					// 解析数据
					const item = JSON.parse(decodeURIComponent(options.data));
					this.email = item;
					
					// 动态设置导航栏标题为发件人，体验更好
					uni.setNavigationBarTitle({
						title: item.sender || '邮件详情'
					});
				} catch (e) {
					console.error("解析邮件数据失败", e);
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #ffffff;
	}
	
	.page-wrapper {
		padding: 30rpx;
	}

	.subject {
		font-size: 40rpx;
		font-weight: bold;
		color: #333;
		margin-bottom: 30rpx;
		line-height: 1.4;
	}

	.sender-info {
		display: flex;
		align-items: center;
		margin-bottom: 30rpx;

		.avatar {
			width: 80rpx;
			height: 80rpx;
			border-radius: 50%;
			color: #fff;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 32rpx;
			margin-right: 20rpx;
		}

		.info-text {
			.name {
				font-size: 30rpx;
				color: #333;
				font-weight: 500;
			}
			.date {
				font-size: 24rpx;
				color: #999;
				margin-top: 4rpx;
			}
		}
	}

	.divider {
		height: 1rpx;
		background-color: #eee;
		margin-bottom: 30rpx;
	}

	.mail-body {
		font-size: 32rpx;
		line-height: 1.8;
		color: #444;
	}
	
	.loading-state {
		text-align: center;
		color: #999;
		margin-top: 100rpx;
	}
</style>