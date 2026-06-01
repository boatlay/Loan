<template>
	<view class="container">
		<view class="nav-bar">
			<view class="iconfont icon-fanhui back-icon" @click="goHome()"></view>
			<text class="title">智能客服</text>
		</view>

		<scroll-view 
			class="chat-content" 
			scroll-y="true" 
			:scroll-top="scrollTop"
			:scroll-with-animation="true"
		>
			<view class="msg-list">
				<view 
					v-for="(item, index) in msgList" 
					:key="index"
					:class="['msg-item', item.role === 'user' ? 'right' : 'left', 'animate-fade-up']"
				>
					<view class="msg-col">
						<view :class="['avatar-circle', item.role === 'user' ? 'user-avatar' : 'ai-avatar']">
							{{ item.role === 'user' ? '我' : 'AI' }}
						</view>
						<view :class="['bubble', item.role === 'user' ? 'user-bubble' : 'ai-bubble']">
							<text class="content-text">{{ item.content }}</text>
						</view>
					</view>
				</view>
			</view>
			<view style="height: 60rpx;"></view>
		</scroll-view>

		<view class="footer-input">
			<view class="input-wrapper">
				<input 
					class="input-box" 
					type="text" 
					v-model="inputText" 
					placeholder="请输入内容..." 
					confirm-type="send" 
					@confirm="handleSend"
				/>
				<view class="send-btn" @click="handleSend">发送</view>
			</view>
		</view>
	</view>
</template>

<script>
    /* 此处完全保留你上一版的 script 内容，逻辑无需任何修改 */
	export default {
		data() {
			return {
				inputText: "", 
				msgList: [
					{ role: 'ai', content: '您好！我是您的专属智能信贷客服，请问有什么可以帮您？', time: new Date().getTime() }
				],   
				scrollTop: 0,
				memoryId: '' ,
			}
		},
		onLoad() {
			this.initMemoryId();
		},
		methods: {
			initMemoryId() {
				const timestamp = new Date().getTime();
				const randomStr = Math.random().toString(36).substring(2, 8);
				this.memoryId = `session_${timestamp}_${randomStr}`;
			},
			goHome() {
				uni.switchTab({
					url: '/pages/index/index'
				});
			},
			async handleSend() {
				if (!this.inputText.trim()) return;
				const currentMessage = this.inputText;
				this.msgList.push({ role: 'user', content: currentMessage, time: new Date().getTime() });
				this.inputText = "";
				this.scrollToBottom();
				const aiMsgIndex = this.msgList.length;
				this.msgList.push({ role: 'ai', content: '思考中...', time: new Date().getTime() });
				this.scrollToBottom();
				const header = {
					'content-type': 'application/json',
					'name': encodeURIComponent((getApp().globalData && getApp().globalData.username) || '') 
				};
				// #ifdef H5
				const requestUrlStreaming = 'http://localhost:8080/ai/chat'; 
				let isFirstChunk = true;
				let textBuffer = '';
				let textDecoder = null;
				try { textDecoder = new TextDecoder('utf-8'); } catch (e) {}
				const processStreamText = (text) => {
					if (isFirstChunk) { this.msgList[aiMsgIndex].content = ''; isFirstChunk = false; }
					textBuffer += text;
					let events = textBuffer.split(/\n\n|\r\n\r\n/);
					textBuffer = events.pop();
					for (let i = 0; i < events.length; i++) {
						let eventStr = events[i];
						let lines = eventStr.split('\n');
						for (let j = 0; j < lines.length; j++) {
							let line = lines[j];
							if (line.startsWith('data:')) {
								let chunkStr = line.substring(5); 
								if (chunkStr.trim() !== '[DONE]') {
									chunkStr = chunkStr.replace(/^"|"$/g, '');
									chunkStr = chunkStr.replace(/\\n/g, '\n');
									this.msgList[aiMsgIndex].content += chunkStr;
								}
							}
						}
					}
					this.scrollToBottom();
				};
				try {
					const urlWithParams = `${requestUrlStreaming}?memoryId=${encodeURIComponent(this.memoryId)}&message=${encodeURIComponent(currentMessage)}`;
					const response = await fetch(urlWithParams, { method: 'GET', headers: header });
					const reader = response.body.getReader();
					while (true) {
						const { done, value } = await reader.read();
						if (done) break;
						let text = textDecoder ? textDecoder.decode(value, { stream: true }) : String.fromCharCode.apply(null, value);
						processStreamText(text);
					}
				} catch (error) {
					if (this.msgList[aiMsgIndex].content === '思考中...') this.msgList[aiMsgIndex].content = '网络异常。';
				}
				// #endif
				// #ifndef H5
				const requestUrlApp = 'http://8.156.83.222:8080/ai/chat_for_app'; 
				uni.request({
					url: requestUrlApp, method: 'GET',
					data: { memoryId: this.memoryId, message: currentMessage },
					header: header, timeout: 60000,
					success: (res) => {
						this.msgList[aiMsgIndex].content = res.statusCode === 200 ? res.data : '服务器错误';
						this.scrollToBottom();
					}
				});
				// #endif
			},
			scrollToBottom() {
				this.$nextTick(() => { this.scrollTop = this.msgList.length * 1000; });
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		height: 100vh;
		display: flex;
		flex-direction: column;
		/* 升级 1：背景改用浅冷色调渐变，能更好地衬托红色气泡 */
		background: linear-gradient(to bottom, #f5f7f9, #f0f2f5);
	}

	/* 导航栏：深色向主题色的高级渐变 */
	.nav-bar {
		background: linear-gradient(135deg, #8b0e14 0%, #b1141d 100%);
		padding-top: var(--status-bar-height); 
		padding-bottom: 35rpx; 
		min-height: 120rpx; 
		display: flex;
		align-items: flex-end; 
		justify-content: center;
		position: relative;
		flex-shrink: 0;
		z-index: 10;
		box-shadow: 0 4rpx 20rpx rgba(139, 14, 20, 0.2); /* 红色系投影 */
	
		.back-icon {
			position: absolute; 
			left: 30rpx; 
			color: rgba(255, 255, 255, 0.9); 
			font-size: 44rpx; 
			bottom: 30rpx;
		}
	
		.title {
			color: #ffffff; 
			font-size: 34rpx; 
			font-weight: bold;
			letter-spacing: 2rpx; /* 增加一点字间距提升高级感 */
			margin-bottom: 2rpx;
		}
	}

	/* 气泡上浮动画 */
	@keyframes fadeUp {
		from { opacity: 0; transform: translateY(30rpx); }
		to { opacity: 1; transform: translateY(0); }
	}
	.animate-fade-up {
		animation: fadeUp 0.4s cubic-bezier(0.25, 0.46, 0.45, 0.94) forwards;
	}

	.chat-content {
		flex: 1;
		height: 0;
		padding: 30rpx 24rpx;
		box-sizing: border-box;
	}

	.msg-item {
		display: flex;
		margin-bottom: 45rpx;
		
		&.right {
			justify-content: flex-end; 
			.msg-col { align-items: flex-end; flex-direction: row-reverse; }
			.bubble { margin-right: 22rpx; border-top-right-radius: 4rpx; }
		}
		
		&.left {
			justify-content: flex-start;
			.msg-col { align-items: flex-start; flex-direction: row; }
			.bubble { margin-left: 22rpx; border-top-left-radius: 4rpx; }
		}
		
		.msg-col { display: flex; max-width: 88%; }

		/* 圆形头像增加微弱外发光 */
		.avatar-circle {
			width: 82rpx;
			height: 82rpx;
			border-radius: 50%;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 24rpx;
			color: #fff;
			flex-shrink: 0;
			box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.08);
		}

		.user-avatar { background: linear-gradient(135deg, #666, #444); }
		/* AI头像也加个微渐变 */
		.ai-avatar { background: linear-gradient(135deg, #b1141d, #d32f2f); }

		.bubble {
			padding: 24rpx 32rpx;
			border-radius: 28rpx;
			font-size: 30rpx;
			line-height: 1.6;
			word-wrap: break-word;
			word-break: break-all;
		}

		/* 用户气泡：红向亮红渐变，增加质感 */
		.user-bubble { 
			background: linear-gradient(135deg, #b1141d 0%, #d81e28 100%); 
			color: #ffffff; 
			box-shadow: 0 6rpx 18rpx rgba(177, 20, 29, 0.25);
		}

		/* AI气泡：纯白向极淡的灰渐变 */
		.ai-bubble { 
			background: linear-gradient(to bottom, #ffffff, #fcfcfc); 
			color: #2c3e50; 
			box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.04);
		}
        
        .content-text { white-space: pre-wrap; }
	}

	/* 输入框区域：保持简洁干净 */
	.footer-input {
		background-color: rgba(255, 255, 255, 0.95);
		backdrop-filter: blur(10px); /* 磨砂玻璃效果 */
		border-top: 1rpx solid rgba(0,0,0,0.05);
		padding: 20rpx 24rpx;
		padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
		
		.input-wrapper {
			display: flex;
			align-items: center;
			background-color: #f2f3f5;
			padding: 0 35rpx;
			border-radius: 44rpx;
			height: 92rpx;
			transition: all 0.3s;
			
			&:focus-within {
				background-color: #fff;
				box-shadow: 0 0 0 2rpx rgba(177, 20, 29, 0.3);
			}
		}

		.input-box {
			flex: 1;
			height: 100%;
			font-size: 28rpx;
			color: #333;
		}

		.send-btn {
			margin-left: 20rpx;
			color: #b1141d; 
			font-weight: bold;
			font-size: 30rpx;
			opacity: 0.8;
			
			&:active {
				opacity: 1;
				transform: scale(0.95);
			}
		}
	}
</style>