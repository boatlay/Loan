<template>
	<view class="content">
			<view class="up">
			    	<view class="iconfont icon-fanhui" @click="goToProduct()"></view>
			    	<text>填写用户资质表</text>
			</view>
			<view class="down">
				<view class="form-container">
				      
				      <view class="form-item">
				        <text class="label required">用户姓名</text>
				        <input class="input" type="text" v-model="formData.name" placeholder="请输入真实姓名" />
				      </view>
				
				      <view class="form-item">
				        <text class="label required">婚姻状态</text>
				        <picker 
				          mode="selector" 
				          :range="marriageOptions" 
				          @change="onMarriageChange"
				        >
				          <view :class="formData.marriageStatus ? 'picker-value' : 'picker-placeholder'">
				            {{ formData.marriageStatus || '请选择婚姻状态' }}
				          </view>
				        </picker>
				      </view>
				
				      <view class="form-item">
				        <text class="label required">最高学历</text>
				        <picker 
				          mode="selector" 
				          :range="educationDisplayList" 
				          @change="onEducationChange"
				        >
				          <view :class="currentEduLabel ? 'picker-value' : 'picker-placeholder'">
				            {{ currentEduLabel || '请选择学历' }}
				          </view>
				        </picker>
				      </view>
				
				      <view class="form-item">
				        <text class="label required">职业</text>
				        <input class="input" type="text" v-model="formData.profession" placeholder="请输入您的职业" />
				      </view>
				
				      <view class="form-item">
				        <text class="label required">月收入(元)</text>
				        <input class="input" type="number" v-model="formData.income" placeholder="请输入月收入（整数）" />
				      </view>
				
				      <view class="form-item">
				        <text class="label">社保/公积金缴纳</text>
				        <switch 
				          color="#b1141d" 
				          :checked="formData.socialInsuranceStatus" 
				          @change="onSocialChange" 
				          style="transform:scale(0.8)"
				        />
				      </view>
				
				      <view class="agreement-box">
				        <label class="checkbox-label" @click="toggleCredit">
				          <view class="custom-checkbox" :class="{ 'checked': formData.creditStatus }">
				             <text v-if="formData.creditStatus">✔</text>
				          </view>
				          <text class="agree-text">我已阅读并同意 <text class="link">《隐私协议》</text></text>
				        </label>
				      </view>
				
				    </view>
				
				    <view class="footer-btn">
				      <button class="submit-btn" @click="submitForm()">提交审核</button>
				    </view>
			</view>
	</view>
</template>

<script>
	export default {
	  data() {
	    return {
	      productId: '', 
	      userId: null,
	      
	      formData: {
	        name: '',
	        marriageStatus: '',
	        educationBackground: '',
	        profession: '',
	        income: null,
	        socialInsuranceStatus: false,
	        creditStatus: false
	      },
	
	      currentEduLabel: '',
	
	      marriageOptions: ['未婚', '已婚', '离异'],
	
	      educationOptions: [
	        { label: '小学', value: 'primary' },
	        { label: '初中', value: 'junior' },
	        { label: '中专', value: 'secondary_technical' },
	        { label: '高中', value: 'senior' },
	        { label: '大专', value: 'junior_college' },
	        { label: '本科', value: 'undergraduate' },
	        { label: '研究生', value: 'graduate' },
	        { label: '博士', value: 'PhD_student' }
	      ]
	    };
	  },
	  computed: {
	    educationDisplayList() {
	      return this.educationOptions.map(item => item.label);
	    }
	  },
	  onLoad(options) {
	    if (options.productId) {
	      this.productId = options.productId;
	      console.log('当前申请的产品ID:', this.productId);
	    }
	  },
	  methods: {
		goToProduct(){
			uni.redirectTo({
				url:"/pages/product/product"
			})
		},
		  
	    onMarriageChange(e) {
	      this.formData.marriageStatus = this.marriageOptions[e.detail.value];
	    },
	
	    onEducationChange(e) {
	      const index = e.detail.value;
	      const selected = this.educationOptions[index];
	      this.currentEduLabel = selected.label;           
	      this.formData.educationBackground = selected.value;
	    },
	
	    onSocialChange(e) {
	      this.formData.socialInsuranceStatus = e.detail.value;
	    },
	
	    toggleCredit() {
	      this.formData.creditStatus = !this.formData.creditStatus;
	    },
	
	    async submitForm() {
	      if (!this.formData.name) return uni.showToast({ title: '请输入姓名', icon: 'none' });
	      if (!this.formData.marriageStatus) return uni.showToast({ title: '请选择婚姻状态', icon: 'none' });
	      if (!this.formData.educationBackground) return uni.showToast({ title: '请选择学历', icon: 'none' });
	      if (!this.formData.profession) return uni.showToast({ title: '请输入职业', icon: 'none' });
	      if (!this.formData.income) return uni.showToast({ title: '请输入月收入', icon: 'none' });
	      if (!this.formData.creditStatus) return uni.showToast({ title: '请先同意征信授权', icon: 'none' });
	
	      uni.showLoading({ title: '提交中...' });
	
	      // 核心修复：在这里做数据类型转换，满足后端 Boolean 类型的要求
	      let booleanMarriageStatus = false;
	      if (this.formData.marriageStatus === '已婚') {
	          booleanMarriageStatus = true;
	      } else {
	          // '未婚' 和 '离异' 都作为 false 传给后端
	          booleanMarriageStatus = false; 
	      }

	      try {
	          let res = await uni.$http.post('/loan/qualification', {
	              name: this.formData.name,
	              marriageStatus: booleanMarriageStatus, // 发送转换后的 Boolean 值
	              educationBackground: this.formData.educationBackground,
	              profession: this.formData.profession,
	              // 保证收入传给后端的是数字类型而不是字符串
	              income: Number(this.formData.income), 
	              socialInsuranceStatus: this.formData.socialInsuranceStatus,
	              creditStatus: this.formData.creditStatus
	          });
	          
	          uni.hideLoading();
	          
	          if(res.code == 200){
	              uni.showToast({ title: '提交成功' });
	              this.userId = res.data;
	              setTimeout(() => {
	                  uni.redirectTo({url:`/pages/tables/apply?userId=${this.userId}&productId=${this.productId}`})
	              }, 1500);
	          }
	      } catch (e) {
	          uni.hideLoading();
	          console.error("提交表单失败:", e);
	          uni.showToast({ title: '提交失败，请检查网络或联系客服', icon: 'none' });
	      }
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
				margin-left: 225rpx;
			}
		}
		
		.down{
			padding-top: 180rpx;
		}
		
		.form-container {
		  margin: 0rpx 30rpx 40rpx;
		  background-color: #fff;
		  border-radius: 20rpx;
		  padding: 0 30rpx;
		  box-shadow: 0 4rpx 16rpx rgba(177, 20, 29, 0.08);
		}
		
		.form-item {
		  display: flex;
		  align-items: center;
		  justify-content: space-between;
		  padding: 36rpx 0;
		  border-bottom: 1px solid #f5f5f5;
		}
		
		.label {
		  font-size: 30rpx;
		  color: #333;
		  width: 240rpx;
		}
		
		.required::before {
		  content: '*';
		  color: #b1141d;
		  margin-right: 4rpx;
		}
		
		.input {
		  flex: 1;
		  text-align: right;
		  font-size: 30rpx;
		  color: #333;
		}
		
		.picker-value {
		  font-size: 30rpx;
		  color: #333;
		}
		
		.picker-placeholder {
		  font-size: 30rpx;
		  color: #ccc;
		}
		
		.arrow {
		  color: #ccc;
		  margin-left: 10rpx;
		  font-size: 28rpx;
		}
		
		.agreement-box {
		  padding: 40rpx 0;
		  display: flex;
		  justify-content: center;
		}
		
		.checkbox-label {
		  display: flex;
		  align-items: center;
		}
		
		.custom-checkbox {
		  width: 36rpx;
		  height: 36rpx;
		  border: 2rpx solid #ccc;
		  border-radius: 50%;
		  margin-right: 12rpx;
		  display: flex;
		  align-items: center;
		  justify-content: center;
		  color: #fff;
		  font-size: 24rpx;
		  transition: all 0.3s;
		}
		
		.custom-checkbox.checked {
		  background-color: #b1141d;
		  border-color: #b1141d;
		}
		
		.agree-text {
		  font-size: 26rpx;
		  color: #666;
		}
		
		.link {
		  color: #b1141d;
		  font-weight: bold;
		}
		
		.footer-btn {
		  padding: 0 40rpx;
		}
		
		.submit-btn {
		  background-color: #b1141d;
		  color: #fff;
		  border-radius: 44rpx;
		  font-size: 32rpx;
		  height: 88rpx;
		  line-height: 88rpx;
		  box-shadow: 0 8rpx 16rpx rgba(177, 20, 29, 0.2);
		}
		
		.submit-btn:active {
		  background-color: #8e1017;
		}
</style>