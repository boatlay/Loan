<template>
	<view class="container">
		<view class="header-banner">
			<text class="banner-title">最新动态</text>
			<text class="banner-sub">随时掌握重要通知</text>
		</view>

		<view class="notice-list">
			<view 
				class="notice-item" 
				v-for="(item, index) in noticeList" 
				:key="index"
				@click="goToDetail(item)"
			>
				<view class="item-main">
					<view class="item-title">{{ item.title }}</view>
					<view class="item-time">{{ item.time }}</view>
				</view>
				<view class="item-arrow">></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 模拟数据 (实际开发中请从API获取)
				noticeList: [
					{
						id: 1,
						title: "欢迎使用'放心用'App",
						time: "2025-12-18 10:00",
						content: "尊敬的用户：<br>欢迎使用'放心用'App,请放心使用"
					},
					{
						id: 2,
						title: "国庆节放假安排公告",
						time: "2025-12-18 09:30",
						content: "根据国家法定节假日规定，我司国庆放假安排如下：10月1日至10月7日放假，共7天。10月8日正常上班。"
					},
					{
						id: 3,
						title: "新版本 V2.0 功能上线说明",
						time: "2025-12-18 14:20",
						content: "新版本增加了验证码功能、性能优化提升了，欢迎大家更新体验。"
					}
				]
			}
		},
		methods: {
			goToDetail(item) {
							// 跳转到详情页，并将对象转为字符串传递
							// 注意：实际项目中如果内容过长，建议只传ID，在详情页重新请求接口
							uni.navigateTo({
								url: `/pages/tables/detail?data=${encodeURIComponent(JSON.stringify(item))}`
							})
			}
		}
	}
</script>

<style lang="scss" scoped>
	/* 页面背景 */
	page {
		background-color: #f5f5f5;
	}

	.header-banner {
		background-color: #b1141d;
		padding: 40rpx 30rpx;
		color: #ffffff;
		display: flex;
		flex-direction: column;
		box-shadow: 0 4rpx 10rpx rgba(177, 20, 29, 0.3);
		text-align: center;
		
		.banner-title {
			font-size: 40rpx;
			font-weight: bold;
			margin-bottom: 10rpx;
		}
		
		.banner-sub {
			font-size: 24rpx;
			opacity: 0.8;
		}
	}

	.notice-list {
		padding: 20rpx;
	}

	.notice-item {
		background-color: #ffffff;
		border-radius: 12rpx;
		padding: 30rpx;
		margin-bottom: 20rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
		/* 左侧装饰条，呼应主题色 */
		border-left: 8rpx solid #b1141d;

		&:active {
			background-color: #f9f9f9;
		}

		.item-main {
			flex: 1;
			margin-right: 20rpx;
		}

		.item-title {
			font-size: 32rpx;
			color: #333;
			font-weight: 500;
			margin-bottom: 12rpx;
			/* 标题超出两行省略 */
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 2;
			overflow: hidden;
		}

		.item-time {
			font-size: 24rpx;
			color: #999;
		}

		.item-arrow {
			color: #ccc;
			font-size: 32rpx;
		}
	}
</style>