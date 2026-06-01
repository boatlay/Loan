<template>
    <view class="page-container">
        <view class="header-bg">
            <view class="nav-bar">
                <view class="iconfont icon-fanhui back-icon" @click="comeToMain()"></view>
                <navigator url="/pages/tables/Register" open-type="navigate" class="register-nav">
                    <text class="register-text">没有账号？立即注册</text>
                </navigator>
            </view>
            
            <view class="greeting-box">
                <text class="title">你好！</text>
                <text class="subtitle">欢迎登录"放心用"贷款APP</text>
            </view>
        </view>
        
        <view class="login-card">
            <view class="input-group">
                <view class="input-item">
                    <input type="text" focus placeholder="请输入用户昵称" placeholder-class="ph-color" v-model="username"/>
                </view>
                
                <view class="input-item">
                    <input type="password" placeholder="请输入用户密码" placeholder-class="ph-color" v-model="password"/>
                </view>
                
                <view class="input-item captcha-group">
                    <input class="captcha-input" type="text" placeholder="请输入验证码" placeholder-class="ph-color" v-model="captchaCode"/>
                    <view class="captcha-img-wrapper">
                        <image :src="captchaImage" class="captcha-img" @click="getCaptcha()" mode="aspectFit"></image>
                    </view>
                </view>
            </view>
            
            <view class="agreement-box">
                <lCheckbox icon="circle" checkedColor="#C12A34" iconSize="32rpx" @click="hasClick()">
                    <view class="agreement-text">
                        我已阅读并同意
                        <text class="highlight">《用户注册协议》</text>和
                        <text class="highlight">《隐私政策》</text>
                    </view>
                </lCheckbox>
            </view>
            
            <button class="login-btn" :disabled="canClick" @click="login()">立即登录</button>
            
            <view class="action-links">
                <text class="link-item" @click="goToEmailLogin()">忘记密码？</text>
                <text class="divider">|</text>
                <text class="link-item" @click="goToEmailLogin()">其他登录方式</text>
            </view>
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
                password:'',
                // 新增验证码相关变量
                captchaCode: '', // 用户输入的验证码
                captchaId: '',   // 后端返回的验证码ID
                captchaImage: '' // Base64图片字符串
            };
        },
        // 页面加载时获取验证码
        onLoad() {
            this.getCaptcha();
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
			goToEmailLogin(){
				uni.navigateTo({
					url:"/pages/tables/EmailLogin"
				})
			},
            // 新增：获取验证码方法
            async getCaptcha() {
                // 如果已有ID，传给后端以复用或更新（取决于你后端逻辑，这里传参是为了配合你的后端Optional逻辑）
                // 注意：这里假设你的uni.$http.get支持params参数
                let res = await uni.$http.get('/Captcha/graph-captcha', {
                                    params: {
                                        captchaId: this.captchaId
                                    }
                                });
                
				const data = res.data || res; 
				
				    if (data && data.captchaImage) {
				        this.captchaId = data.captchaId;
				
				        // ★★★ 关键点 2：不要手动拼接前缀！ ★★★
				        // 后端给的数据里已经有 "data:image/png;base64," 了
				        // 如果你再加，就会变成 "data:image...data:image..." 导致图片裂开
				        this.captchaImage = data.captchaImage; 
				        
				        // 清空输入框
				        this.captchaCode = ''; 
				    }
            },
            
            async login(){
                            // 登录请求加入验证码参数
                            let res = await uni.$http.post('/users/login',{
                                name: this.username,
                                password: this.password,
                                captchaId: this.captchaId,     
                                captchaCode: this.captchaCode  
                            })
                            
                            if(res.code == 200){
                                // 🌟 修改核心：后端把 token 放在了 data 里
                                const token = res.data; 
                                
                                // 1. 存入全局变量，供 request.js 使用
                                getApp().globalData.username = this.username;
                                getApp().globalData.token = token;
                                
                                // 2. 存入本地缓存，保证杀掉 App 再次打开时不需要重新登录
                                uni.setStorageSync('username', this.username);
                                uni.setStorageSync('token', token);
                                
                                getApp().globalData.hasCheckedPermission = false; 
                                
                                uni.switchTab({
                                    url:"/pages/index/index"
                                });
                            } else {
                                uni.showToast({
                                    title: res.msg || '登录失败',
                                    icon: 'none'
                                });
                                this.getCaptcha();
                            }
                        }
        },
        computed:{
            canClick(){
                // 增加验证码非空判断
                if(this.username.length > 0 && this.password.length > 0 && this.captchaCode.length > 0 && this.clicked){
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
    .page-container {
        min-height: 100vh;
        background-color: #F5F7FA; /* 极简浅灰背景，衬托白色卡片 */
        font-family: -apple-system, BlinkMacSystemFont, 'Helvetica Neue', Helvetica, sans-serif;
    }

    /* 顶部红色品牌底座 */
    .header-bg {
        width: 100%;
        height: 480rpx;
        background: linear-gradient(135deg, #E63946, #B71C1C); /* 高级渐变红 */
        border-bottom-left-radius: 60rpx;
        border-bottom-right-radius: 60rpx;
        padding-top: 90rpx;
        box-sizing: border-box;
        position: relative;
        z-index: 1;
    }

    /* 顶部导航栏 */
    .nav-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 40rpx;
        
        .back-icon {
            font-size: 40rpx;
            color: #ffffff;
            padding: 10rpx;
        }
        
        .register-text {
            font-size: 28rpx;
            color: rgba(255, 255, 255, 0.9);
            font-weight: 500;
        }
    }

    /* 欢迎语区域 */
    .greeting-box {
        padding: 60rpx 50rpx;
        
        .title {
            display: block;
            font-size: 56rpx;
            font-weight: bold;
            color: #ffffff;
            margin-bottom: 16rpx;
            letter-spacing: 2rpx;
        }
        
        .subtitle {
            display: block;
            font-size: 30rpx;
            color: rgba(255, 255, 255, 0.8);
            letter-spacing: 1rpx;
        }
    }

    /* 核心悬浮卡片 */
    .login-card {
        margin: -80rpx 40rpx 0; /* 负边距实现悬浮效果 */
        background-color: #ffffff;
        border-radius: 32rpx;
        padding: 60rpx 40rpx;
        box-shadow: 0 16rpx 40rpx rgba(183, 28, 28, 0.08); /* 品牌色微阴影 */
        position: relative;
        z-index: 2;
    }

    /* 输入框组 */
    .input-group {
        margin-bottom: 40rpx;
    }

    /* 单个输入框容器：胶囊拟物化设计 */
    .input-item {
        background-color: #F8F9FA;
        border-radius: 20rpx;
        height: 100rpx;
        margin-bottom: 30rpx;
        display: flex;
        align-items: center;
        padding: 0 30rpx;
        border: 2rpx solid transparent;
        transition: all 0.3s;
        
        &:focus-within {
            background-color: #ffffff;
            border-color: #E63946; /* 聚焦时边框变红 */
            box-shadow: 0 4rpx 16rpx rgba(230, 57, 70, 0.1);
        }
        
        input {
            flex: 1;
            height: 100%;
            font-size: 32rpx;
            color: #333333;
            background: transparent;
        }
        
        .ph-color {
            color: #B0B0B0;
        }
    }

    /* 验证码特殊布局 */
    .captcha-group {
        background: transparent; /* 移除外层背景 */
        padding: 0;
        display: flex;
        justify-content: space-between;
        border: none;
        
        &:focus-within {
            box-shadow: none;
            border-color: transparent;
        }
        
        .captcha-input {
            background-color: #F8F9FA;
            border-radius: 20rpx;
            height: 100rpx;
            width: 340rpx;
            padding: 0 30rpx;
            box-sizing: border-box;
            border: 2rpx solid transparent;
            transition: all 0.3s;
            
            &:focus {
                background-color: #ffffff;
                border-color: #E63946;
            }
        }
        
        .captcha-img-wrapper {
            width: 240rpx;
            height: 100rpx;
            border-radius: 20rpx;
            background-color: #ffffff;
            box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
            overflow: hidden;
            
            .captcha-img {
                width: 100%;
                height: 100%;
            }
        }
    }

    /* 协议区域 */
    .agreement-box {
        margin-bottom: 50rpx;
        
        .agreement-text {
            font-size: 26rpx;
            color: #888888;
            line-height: 1.5;
            margin-left: 10rpx;
        }
        
        .highlight {
            color: #C12A34; /* 品牌红 */
            font-weight: 500;
        }
    }

    /* 登录按钮定制 */
    .login-btn {
        background: linear-gradient(135deg, #E63946, #B71C1C);
        color: #ffffff;
        border-radius: 50rpx;
        height: 96rpx;
        line-height: 96rpx;
        font-size: 34rpx;
        font-weight: bold;
        box-shadow: 0 12rpx 24rpx rgba(183, 28, 28, 0.25);
        transition: all 0.3s;
        
        &::after {
            border: none; /* 移除 uni-app 默认的按钮边框 */
        }
        
        /* 禁用状态的优雅降级 */
        &[disabled] {
            background: #E0E0E0 !important;
            color: #999999 !important;
            box-shadow: none !important;
        }
        
        &:active:not([disabled]) {
            transform: scale(0.98);
            box-shadow: 0 4rpx 10rpx rgba(183, 28, 28, 0.15);
        }
    }

    /* 底部辅助链接 */
    .action-links {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 50rpx;
        padding-bottom: 20rpx;
        
        .link-item {
            font-size: 28rpx;
            color: #666666;
            transition: color 0.2s;
            
            &:active {
                color: #C12A34;
            }
        }
        
        .divider {
            margin: 0 30rpx;
            color: #E0E0E0;
            font-size: 24rpx;
        }
    }
</style>