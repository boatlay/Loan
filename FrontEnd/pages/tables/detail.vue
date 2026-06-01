<template>
	<view class="detail-container">
		<view class="header">
			<view class="title">{{ noticeData.title }}</view>
			<view class="meta">
				<text class="time">{{ noticeData.time }}</text>
				<text class="author">系统管理员</text>
			</view>
		</view>

		<view class="divider"></view>

		<view class="content">
			<rich-text :nodes="noticeData.content"></rich-text>
		</view>
	</view>
</template>

<script>
	export default {
			data() {
				return {
					noticeData: {}
				}
			},
			onLoad(options) {
				// 接收上个页面传来的参数
				if (options.data) {
					try {
						this.noticeData = JSON.parse(decodeURIComponent(options.data));
					} catch (e) {
						console.error('数据解析失败', e);
					}
				}
			}
		}
</script>

<style lang="scss" scoped>
	page {
		background-color: #ffffff;
	}

	.detail-container {
		padding: 40rpx 30rpx;
	}

	.header {
		margin-bottom: 30rpx;
		
		.title {
			font-size: 44rpx;
			font-weight: bold;
			color: #333;
			line-height: 1.4;
			margin-bottom: 20rpx;
		}
		
		.meta {
			display: flex;
			align-items: center;
			font-size: 26rpx;
			color: #888;
			
			.time {
				margin-right: 20rpx;
			}
			
			.author {
				color: #b1141d; /* 使用主题色高亮发布者 */
			}
		}
	}

	.divider {
		height: 1rpx;
		background-color: #eeeeee;
		margin-bottom: 40rpx;
	}

	.content {
		font-size: 32rpx;
		color: #444;
		line-height: 1.8;
		text-align: justify;
	}
</style>