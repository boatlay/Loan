<template>
	<view class="email-container">
		<view class="email-list">
			<view 
				class="email-item" 
				v-for="(item, index) in emailList" 
				:key="item.id"
				@click="goToDetail(item)"
				:class="{ 'is-read': item.isRead }"
			>
				<view class="avatar" :style="{ backgroundColor: item.avatarColor }">
					{{ item.sender[0] }}
				</view>
				
				<view class="content-box">
					<view class="header-row">
						<text class="sender">{{ item.sender }}</text>
						<text class="time">{{ item.date }}</text>
					</view>
					<view class="subject">{{ item.subject }}</view>
					<view class="summary">{{ item.summary }}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 模拟邮件数据
				emailList: [
					{
						id: 101,
						sender: "管理员",
						subject: "2025贷款结果确认",
						summary: "各位同事，2025贷款结果已经生成，请登录系统查看...",
						content: "各位用户：<br><br>2025贷款审核工作已结束。请登录系统查看您的最终贷款结果。<br>如有异议，请在3个工作日内回复本邮件。",
						date: "14:20",
						isRead: false,
						avatarColor: "#b1141d" // 使用主题色
					},
					{
						id: 102,
						sender: "管理员",
						subject: "【安全提醒】请及时修改您的登录密码",
						summary: "检测到您的账号在非常用地点登录，为了安全起见...",
						content: "检测到您的账号在非常用地点（IP: 192.168.x.x）尝试登录。<br>如果这不是您本人的操作，请立即点击下方链接修改密码。",
						date: "昨天",
						isRead: true,
						avatarColor: "#4a90e2"
					},
					{
						id: 103,
						sender: "管理员",
						subject: "贷款逾期提醒",
						summary: "您的贷款将再下周一逾期，请及时还款",
						content: "你好，<br>贷款即将预期，请立即还清<span style='color:red'>第1期</span>的贷款。",
						date: "12/18",
						isRead: true,
						avatarColor: "#f5a623"
					}
				]
			}
		},
		methods: {
			goToDetail(item) {
				// 标记为已读（本地模拟）
				item.isRead = true;
				
				// 跳转详情页
				uni.navigateTo({
					url: `/pages/tables/detail?data=${encodeURIComponent(JSON.stringify(item))}`
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #f7f7f7;
	}

	.email-item {
		background-color: #ffffff;
		padding: 24rpx;
		display: flex;
		border-bottom: 1rpx solid #eeeeee;
		
		&:active {
			background-color: #f0f0f0;
		}

		/* 已读邮件样式变淡 */
		&.is-read {
			.sender, .subject {
				font-weight: normal;
				color: #666;
			}
			.avatar {
				opacity: 0.6;
			}
		}
	}

	.avatar {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		color: #fff;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 36rpx;
		margin-right: 24rpx;
		flex-shrink: 0;
	}

	.content-box {
		flex: 1;
		overflow: hidden; /* 防止文字溢出撑开 */
	}

	.header-row {
		display: flex;
		justify-content: space-between;
		margin-bottom: 8rpx;
		
		.sender {
			font-size: 30rpx;
			font-weight: bold;
			color: #333;
		}
		
		.time {
			font-size: 24rpx;
			color: #999;
		}
	}

	.subject {
		font-size: 28rpx;
		color: #333;
		font-weight: bold;
		margin-bottom: 6rpx;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.summary {
		font-size: 26rpx;
		color: #999;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}
</style>