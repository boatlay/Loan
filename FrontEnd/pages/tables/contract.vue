<template>
	<view class="content">
		<view class="up">
		    	<view class="iconfont icon-fanhui" @click="goToProduct()"></view>
		    	<text>填写合同相关内容</text>
		</view>
		<view class="down">
			<view class="section-card">
			      <view class="section-title">
			        <text class="icon">📜</text>
			        <text>信用宣誓</text>
			      </view>
			      
			      <view class="oath-text-box">
			        <text class="oath-content">本人郑重承诺：所提供的个人资料及申请信息真实、有效。本人将严格遵守借款合同约定，按时足额归还本金及利息，珍惜个人信用记录。</text>
			      </view>
			
			      <view class="input-area">
			        <text class="input-tip">请在下方输入框中输入<text class="highlight">上方宣誓内容</text>以确认</text>
			        <input 
			          class="oath-input" 
			          type="text" 
			          v-model="userOath" 
			          placeholder="本人郑重承诺：......"
			        />
			      </view>
			    </view>
			
			    <view class="section-card">
			      <view class="section-title">
			        <text class="icon">✍️</text>
			        <text>电子签名</text>
			      </view>
			      
			      <view class="signature-box">
			        <canvas 
			          class="signature-canvas" 
			          canvas-id="signatureCanvas"
			          @touchstart="uploadScaleStart"
			          @touchmove="uploadScaleMove"
			          @touchend="uploadScaleEnd"
			          disable-scroll="true"
			        ></canvas>
			        <view class="placeholder-text" v-if="!hasSigned">请在此区域手写签名</view>
			      </view>
			
			      <view class="canvas-btns">
			        <button class="btn btn-clear" @click="clearSignature">重签</button>
			      </view>
			    </view>
			
			    <view class="footer-btn">
			      <button class="submit-btn" @click="submitContract">确认贷款</button>
			    </view>
			
			    <view class="modal-mask" v-if="showSuccessModal" @touchmove.stop.prevent>
			      <view class="modal-box">
			        <view class="modal-icon">🎉</view>
			        <view class="modal-title">提交成功</view>
			        <view class="modal-desc">贷款申请已提交，请等待放款结果</view>
			        <view class="modal-info">合同编号: {{ contractId }}</view>
			        
			        <button class="modal-btn" @click="handleSuccessConfirm">确认</button>
			      </view>
			    </view>
		</view>
	</view>
</template>

<script>
	export default {
	  data() {
	    return {
	      userId: '',
	      applyId: '',
	      contractId: null,
	
	      userOath: '',
	      
	      ctx: null,
	      hasSigned: false,
	      
	      showSuccessModal: false
	    };
	  },
	  onLoad(options) {
	    if (options.userId) this.userId = options.userId;
	    if (options.applyId) this.applyId = options.applyId;
	    
	    console.log(`签署页加载 - UserId: ${this.userId}, ApplyId: ${this.applyId}`);
	
	    this.ctx = uni.createCanvasContext('signatureCanvas', this);
	    this.ctx.setLineWidth(4);
	    this.ctx.setLineCap('round');
	    this.ctx.setStrokeStyle('#000000');
		
		this.fetchRiskScore();
	  },
	  methods: {
		
		async fetchRiskScore() {
		            try {
		                // 注意：由于你的 Java Controller 期望 @GetMapping("/calculate_score")
		                // 使用 uni.$http.get 发起 GET 请求。
		                // 你的 Java 后端要求 @RequestHeader String token。
		                // 假设你的 uni.$http 全局拦截器已经自动在 header 中加入了 token，这里就直接调用即可。
		                // 如果没有全局拦截器，你需要写成：uni.$http.get('/calculate_score', { header: { token: uni.getStorageSync('token') } })
		                
		                let res = await uni.$http.get('/calculate_score');
		                
		                // 根据你的 ResponseResult 结构，通常会有 code 判断
		                if (res.code === 200) {
		                    console.log('风控评分计算成功', res);
		                    // 如果后端返回了具体分数，可以在这里赋值，例如：
		                    // this.riskScore = res.data;
		                } else {
		                    console.log('风控评分计算返回异常', res.msg);
		                }
		            } catch (error) {
		                console.error('调用风控评分接口失败', error);
		            }
		        },
		  
		  
		goToProduct(){
			uni.redirectTo({
				url:"/pages/product/product"
			})
		},
		  
	    uploadScaleStart(e) {
	      this.hasSigned = true;
	      const { x, y } = e.changedTouches[0];
	      this.ctx.beginPath();
	      this.ctx.moveTo(x, y);
	    },
	    uploadScaleMove(e) {
	      const { x, y } = e.changedTouches[0];
	      this.ctx.lineTo(x, y);
	      this.ctx.stroke();
	      this.ctx.draw(true);
	    },
	    uploadScaleEnd() {
	    },
	    clearSignature() {
	      this.ctx.clearRect(0, 0, 1000, 1000);
	      this.ctx.draw();
	      this.hasSigned = false;
	    },
	
	    async submitContract() {
	      if (this.userOath.trim() !== '本人郑重承诺：所提供的个人资料及申请信息真实、有效。本人将严格遵守借款合同约定，按时足额归还本金及利息，珍惜个人信用记录。') {
	        return uni.showToast({ title: '请正确输入宣誓内容', icon: 'none' });
	      }
	      if (!this.hasSigned) {
	        return uni.showToast({ title: '请先完成电子签名', icon: 'none' });
	      }
	
	      uni.showLoading({ title: '签署中...' });
	
	
			let res=await uni.$http.post('/loan/contract',{
				userId:this.userId,
				applyId:this.applyId
			})
	      // setTimeout(() => {
	//         const res = {
	//           data: 2567,
	//         };
	
	//         this.contractId = res.data;
	        
	//         console.log('签署成功');
	//         console.log('UserId:', this.userId);
	//         console.log('ApplyId:', this.applyId);
	//         console.log('ContractId:', this.contractId);
	
	        uni.hideLoading();
			
			if(res.code==200){
				this.contractId=res.data
				
				let res1=await uni.$http.post('/loan/plan',{
					contractId:this.contractId
				})
				
				this.showSuccessModal = true;
			}
	
	      // }, 1500);
	    },
	
	    handleSuccessConfirm() {
	      this.showSuccessModal = false;
		  uni.redirectTo({
		  	url:"/pages/product/product"
		  })
	    }
	  }
	};
</script>

<style scoped lang="less">
	.content {
				min-height: 100vh;
				background-color: #f7f7f7;
				}
		
		.up{
			box-sizing: border-box;
			padding-top: 75rpx;
			margin-bottom: 20rpx;
			
			position: fixed;
			display: flex;
			align-items: center;
			
			z-index: 3;
			width: 750rpx;
			height: 150rpx;
			background-color: #ffffff;
			
			box-shadow: 0 5rpx 15rpx rgba(0,0,0,0.05);
			
			.iconfont{
				font-size: 45rpx;
			}
			
			text{
				display: block;
				font-size:35rpx;
				margin-left: 210rpx;
			}
		}
		
		.down{
			padding-top: 180rpx;
		}
		
		.section-card {
		  margin: 0rpx 30rpx 30rpx;
		  background-color: #fff;
		  border-radius: 20rpx;
		  padding: 30rpx;
		  box-shadow: 0 4rpx 16rpx rgba(177, 20, 29, 0.05);
		}
		
		.section-card:first-child {
		}
		
		.section-title {
		  display: flex;
		  align-items: center;
		  font-size: 30rpx;
		  font-weight: bold;
		  color: #333;
		  margin-bottom: 24rpx;
		  border-bottom: 1px solid #f5f5f5;
		  padding-bottom: 16rpx;
		}
		
		.section-title .icon {
		  margin-right: 12rpx;
		  font-size: 32rpx;
		}
		
		.oath-text-box {
		  background-color: #f9f9f9;
		  padding: 20rpx;
		  border-radius: 12rpx;
		  margin-bottom: 30rpx;
		}
		
		.oath-content {
		  font-size: 26rpx;
		  color: #555;
		  line-height: 1.6;
		  text-align: justify;
		}
		
		.input-tip {
		  font-size: 26rpx;
		  color: #666;
		  margin-bottom: 16rpx;
		  display: block;
		}
		
		.highlight {
		  color: #b1141d;
		  font-weight: bold;
		}
		
		.oath-input {
		  border: 1px solid #ddd;
		  height: 80rpx;
		  border-radius: 12rpx;
		  padding: 0 20rpx;
		  font-size: 30rpx;
		}
		
		.signature-box {
		  width: 100%;
		  height: 300rpx;
		  border: 1px dashed #ccc;
		  border-radius: 12rpx;
		  background-color: #fcfcfc;
		  position: relative;
		  overflow: hidden;
		}
		
		.signature-canvas {
		  width: 100%;
		  height: 100%;
		  z-index: 1;
		}
		
		.placeholder-text {
		  position: absolute;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%, -50%);
		  color: #ddd;
		  font-size: 30rpx;
		  pointer-events: none;
		}
		
		.canvas-btns {
		  display: flex;
		  justify-content: flex-end;
		  margin-top: 20rpx;
		}
		
		.btn-clear {
		  font-size: 24rpx;
		  color: #666;
		  background-color: #f0f0f0;
		  padding: 0 30rpx;
		  height: 60rpx;
		  line-height: 60rpx;
		  border-radius: 30rpx;
		  margin: 0;
		}
		
		.footer-btn {
		  margin-top: 60rpx;
		  padding: 0 40rpx;
		}
		
		.submit-btn {
		  background-color: #b1141d;
		  color: #fff;
		  border-radius: 44rpx;
		  font-size: 32rpx;
		  height: 90rpx;
		  line-height: 90rpx;
		  box-shadow: 0 8rpx 20rpx rgba(177, 20, 29, 0.25);
		}
		
		.submit-btn:active {
		  opacity: 0.9;
		}
		
		.modal-mask {
		  position: fixed;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background-color: rgba(0, 0, 0, 0.6);
		  z-index: 999;
		  display: flex;
		  align-items: center;
		  justify-content: center;
		}
		
		.modal-box {
		  width: 560rpx;
		  background-color: #fff;
		  border-radius: 24rpx;
		  padding: 50rpx 40rpx;
		  display: flex;
		  flex-direction: column;
		  align-items: center;
		  animation: popIn 0.2s ease-out;
		}
		
		@keyframes popIn {
		  from { opacity: 0; transform: scale(0.9); }
		  to { opacity: 1; transform: scale(1); }
		}
		
		.modal-icon {
		  font-size: 80rpx;
		  margin-bottom: 20rpx;
		}
		
		.modal-title {
		  font-size: 36rpx;
		  font-weight: bold;
		  color: #333;
		  margin-bottom: 16rpx;
		}
		
		.modal-desc {
		  font-size: 28rpx;
		  color: #666;
		  text-align: center;
		  margin-bottom: 10rpx;
		}
		
		.modal-info {
		  font-size: 24rpx;
		  color: #999;
		  background-color: #f5f5f5;
		  padding: 4rpx 16rpx;
		  border-radius: 8rpx;
		  margin-bottom: 40rpx;
		  font-family: monospace;
		}
		
		.modal-btn {
		  width: 100%;
		  height: 80rpx;
		  line-height: 80rpx;
		  background-color: #b1141d;
		  color: #fff;
		  font-size: 30rpx;
		  border-radius: 40rpx;
		}
</style>