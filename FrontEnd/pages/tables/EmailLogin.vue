<template>
    <view>
        <view class="up">
            <view class="iconfont icon-fanhui" @click="comeToMain()"></view>
            <text class="text-left">未注册邮箱，</text>
            <navigator url="/pages/login/PhoneLogin" open-type="navigate"><text class="text-right">手机号登录</text></navigator>
        </view>
        
        <view class="middle">
            <view><text class="text-up-up">你好!</text></view>
            <view><text class="text-up">欢迎使用邮箱登录"放心用"</text></view>
            <view>
                <input type="text" focus placeholder="请输入您的QQ邮箱" v-model="email"/>
                
                <view class="code-box">
                    <input class="code-input" type="text" placeholder="请输入邮箱验证码" v-model="emailCode"/>
                    <button class="send-btn" :disabled="isSending" @click="sendMailCode()">{{ sendBtnText }}</button>
                </view>
            </view>
            <view>
                <lCheckbox icon="circle" checkedColor="#fa4d37" iconSize="28rpx" @click="hasClick()">
                    <text class="all">我已阅读并同意<text class="left">《用户注册协议》</text>和<text class="right">《隐私政策》</text></text>
                </lCheckbox>
            </view>
        </view>
        
        <view class="bottom">
            <button type="warn" :disabled="canClick" @click="login()">立即登录</button>
        </view>
    </view>
</template>

<script>
    import LCheckboxGroup from "../../uni_modules/lime-checkbox/components/l-checkbox-group/l-checkbox-group.vue"
    import lCheckbox from "../../uni_modules/lime-checkbox/components/l-checkbox/l-checkbox.vue"
    
    export default{
        data(){
            return{
                active: true,
                clicked: false,
                email: '',
                emailCode: '',
                
                // 验证码发送相关变量
                isSending: false,
                countdown: 60,
                sendBtnText: '获取验证码',
                timer: null
            };
        },
        methods:{
            hasClick(){
                this.clicked = !this.clicked
            },
            comeToMain(){
                uni.switchTab({
                	url:"/pages/main/main"
                })
            },
            
            // 发送邮箱验证码
            async sendMailCode() {
                if (!this.email) {
                    uni.showToast({ title: '请输入邮箱', icon: 'none' });
                    return;
                }
                // 简单的前端拦截，配合后端 UserService 中的校验逻辑
                if (!this.email.endsWith('@qq.com')) {
                    uni.showToast({ title: '请使用QQ邮箱', icon: 'none' });
                    return;
                }

                this.isSending = true;
                this.sendBtnText = '发送中...';

                try {
                    let res = await uni.$http.get('/user/mail/code', {
                        email: this.email 
                    });

                    // 假设你的响应结构中 200 为成功
                    if (res.code === 200 || res.data?.code === 200) {
                        uni.showToast({ title: '验证码已发送', icon: 'success' });
                        this.startCountdown();
                    } else {
                        uni.showToast({ title: res.msg || res.data?.msg || '发送失败', icon: 'none' });
                        this.resetSendBtn();
                    }
                } catch (error) {
                    uni.showToast({ title: '网络请求失败', icon: 'none' });
                    this.resetSendBtn();
                }
            },

            // 倒计时逻辑
            startCountdown() {
                this.countdown = 60;
                this.sendBtnText = `${this.countdown}s 后重发`;
                this.timer = setInterval(() => {
                    this.countdown--;
                    if (this.countdown <= 0) {
                        this.resetSendBtn();
                    } else {
                        this.sendBtnText = `${this.countdown}s 后重发`;
                    }
                }, 1000);
            },

            // 重置发送按钮
            resetSendBtn() {
                clearInterval(this.timer);
                this.isSending = false;
                this.sendBtnText = '获取验证码';
            },
            
            // 登录逻辑
                        async login(){
                            try {
                                // 对接 UserController 的 /user/mail/judge 接口
                                let res = await uni.$http.get('/user/mail/judge', {
                                    code: this.emailCode,
                                    email: this.email
                                });
                                
                                if (res.code === 200 || res.data?.code === 200) {
                                    uni.showToast({ title: '登录成功', icon: 'success' });
                                    
                                    // 登录成功后，保存用户信息并跳转首页
                                    const responseData = res.data.data || res.data;
                                    
                                    getApp().globalData.username = responseData.name; 
                                    getApp().globalData.token = responseData.token;
                                    
                                    uni.setStorageSync('username', responseData.name);
                                    uni.setStorageSync('token', responseData.token);
                                    
                                    setTimeout(() => {
                                        uni.switchTab({
                                            url: "/pages/index/index"
                                        });
                                    }, 500);
                                } else {
                                    uni.showToast({
                                        title: res.msg || res.data?.msg || '验证码错误',
                                        icon: 'none'
                                    });
                                }
                            } catch (error) {
                                uni.showToast({ title: '网络请求失败', icon: 'none' });
                            }
                        }
        },
        computed:{
            canClick(){
                // 增加邮箱和验证码非空判断
                if(this.email.length > 0 && this.emailCode.length > 0 && this.clicked){
                    return !this.active
                }
                return this.active
            }
        },
        components:{
            LCheckboxGroup,
            lCheckbox
        },
        beforeDestroy() {
            // 组件销毁前清除定时器，防止内存泄漏
            if (this.timer) {
                clearInterval(this.timer);
            }
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
            padding-left: 360rpx; /* 稍微调整左侧间距，以适应稍长的右侧文案 */
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
        
        /* 通用输入框样式 */
        input{
            background-color: #e8e8e8;
            border-radius: 10rpx;
            width: 690rpx;
            height: 90rpx;
            padding-left: 20rpx;
            margin-bottom: 20rpx;
            font-size: 40rpx;
            box-sizing: border-box;
        }

        /* 验证码输入框及按钮区域布局 */
        .code-box {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
            width: 690rpx; 
            margin-bottom: 20rpx;
            
            .code-input {
                width: 420rpx; /* 给验证码输入框留出按钮空间 */
                margin-bottom: 0; 
            }
            
            .send-btn {
                width: 250rpx;
                height: 90rpx;
                line-height: 90rpx;
                background-color: #fa4d37;
                color: #ffffff;
                font-size: 30rpx;
                border-radius: 10rpx;
                padding: 0;
                margin: 0;
                
                &::after {
                    border: none; /* 移除uniapp按钮默认边框 */
                }
                
                &[disabled] {
                    background-color: #cccccc !important;
                    color: #ffffff !important;
                }
            }
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
            color: #fa4d37;
        }
    }
    
    .bottom{
        margin:90rpx 20rpx 0 20rpx;
        
        button{
            font-weight: bold;
            color: #e7e7e7;
            font-size: 40rpx;
            width: 690rpx;
        }
    }
</style>