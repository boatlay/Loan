<template>
  <view class="content">
    <view class="up">
    	<view class="iconfont icon-fanhui" @click="goToMain()"></view>
    	<text>清除缓存</text>
    </view>
	<view class="down">
    <view class="settings-list">
      <view class="list-item" @click="openConfirmModal">
        <text class="item-label">清除缓存</text>
      </view>
    </view>

    <view class="modal-mask" v-if="showConfirmModal" @touchmove.stop.prevent>
      <view class="modal-box">
        <view class="modal-title">提示</view>
        <view class="modal-content">确定要清除缓存吗？</view>
        
        <view class="modal-btns double-btn">
          <button class="btn btn-gray" @click="closeConfirmModal">我再想想</button>
          <button class="btn btn-theme" @click="handleConfirmClear">确认</button>
        </view>
      </view>
    </view>

    <view class="modal-mask" v-if="showSuccessModal" @touchmove.stop.prevent>
      <view class="modal-box">
        <view class="modal-title">提示</view>
        <view class="modal-content">清除缓存成功</view>
        
        <view class="modal-btns single-btn">
          <button class="btn btn-theme full-width" @click="closeSuccessModal">确认</button>
        </view>
      </view>
    </view>
	</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      showConfirmModal: false,
      showSuccessModal: false
    };
  },
  methods: {
	goToMain(){
		uni.navigateBack({
							delta:1
						})
	},
	  
    openConfirmModal() {
      this.showConfirmModal = true;
    },

    closeConfirmModal() {
      this.showConfirmModal = false;
    },

    handleConfirmClear() {
      this.showConfirmModal = false;
      
      uni.showLoading({ title: '清理中...' });

      setTimeout(() => {
        uni.hideLoading();
        this.showSuccessModal = true;
      }, 500);
    },

    closeSuccessModal() {
      this.showSuccessModal = false;
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
			margin-left: 270rpx;
		}
	}
	
	.down{
		padding-top: 180rpx;
	}

.settings-list {
	width: 700rpx;
	margin: 0 auto;
	border-radius: 25rpx;
  background-color: #fff;
}
.list-item {
	display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx 40rpx;
  border-bottom: 1px solid #eee;
}
.list-item:active {
  background-color: #f9f9f9;
}
.item-label {
  font-size: 35rpx;
  color: #333;
}
.item-right {
  display: flex;
  align-items: center;
}
.arrow {
  color: #ccc;
  font-size: 26rpx;
}

.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-box {
  width: 600rpx;
  background-color: #ffffff;
  border-radius: 24rpx;
  overflow: hidden;
  padding-top: 40rpx;
  animation: fadeIn 0.2s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: scale(0.9); }
  to { opacity: 1; transform: scale(1); }
}

.modal-title {
  text-align: center;
  font-size: 34rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 20rpx;
}

.modal-content {
  text-align: center;
  font-size: 30rpx;
  color: #666;
  padding: 0 40rpx 50rpx;
}

.modal-btns {
  display: flex;
  padding: 0 40rpx 40rpx;
  justify-content: space-between;
}

.single-btn {
  justify-content: center;
}

.btn {
  height: 80rpx;
  line-height: 80rpx;
  font-size: 30rpx;
  border-radius: 40rpx;
  border: none;
  margin: 0;
}
.btn::after { border: none; }

.btn-gray {
  background-color: #f5f5f5;
  color: #666;
  width: 240rpx;
}

.btn-theme {
  background-color: #b1141d;
  color: #ffffff;
  width: 240rpx;
}

.full-width {
  width: 100%;
}

</style>