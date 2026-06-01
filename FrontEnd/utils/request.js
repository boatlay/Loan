const BASE_URL = 'http://localhost:8080' 
// const BASE_URL = 'http://8.156.83.222:8080'

const request = (options) => {
  return new Promise((resolve, reject) => {

    if (!options.hideLoading) {
      uni.showLoading({
        title: '加载中...',
        mask: true
      });
    }

    // 🌟 获取全局变量，如果 getApp() 还没准备好，则降级从本地缓存拿
    const app = getApp();
    const currentUsername = (app && app.globalData.username) ? app.globalData.username : uni.getStorageSync('username');
    const currentToken = (app && app.globalData.token) ? app.globalData.token : uni.getStorageSync('token');

    let header = {
      'content-type': 'application/json',
      'name': encodeURIComponent(currentUsername || ''),
      // 🌟 核心修改：将 token 塞入请求头
      // 注意：这里的键名 'token' 需要和你后端 Spring Boot 拦截器里 request.getHeader("token") 的名字保持一致
      // 行业规范通常也叫 'Authorization'，你可以根据你的后端代码灵活调整
      'token': currentToken, 
      ...options.header
    };

    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: header,
      timeout: 10000,
      // 🌟 修改：切换成 Token 后，就可以把下面这行关掉了，因为不再需要 Cookie 维持 Session 了
      // withCredentials: true, 

      success: (res) => {
        if (res.statusCode === 200) {
          const resData = res.data;

          if (options.url == 'http://192.168.1.105:8080/other/score') {
            resolve(resData)
          }

          if (resData.code === 200) {
            resolve(resData);
          } else {
            uni.showToast({
              title: resData.msg || '请注意！',
              icon: 'none', 
              duration: 2000
            });

            // 如果后端校验 Token 失效，返回了类似 "登录过期，请重新登录" 的信息，这里刚好能接住
            if (resData.msg == "登录过期，请重新登录") {
              setTimeout(() => {
                // 登录过期时，可以顺手清理掉本地失效的 token
                uni.removeStorageSync('token');
                
                uni.redirectTo({
                  url: "/pages/tables/login"
                })
              }, 2500);
            }

            reject(resData);
          }
        } else {
          uni.showToast({
            title: '网络连接异常，状态码：' + res.statusCode,
            icon: 'none',
            duration: 2000
          });
          reject(res);
        }
      },
      fail: (err) => {
        uni.showToast({
          title: '网络连接超时',
          icon: 'none'
        });
        reject(err);
      },
      complete: () => {
        if (!options.hideLoading) {
          uni.hideLoading();
        }
      }
    });
  });
};

export default {
  get(url, data = {}, options = {}) {
    return request({
      url,
      method: 'GET',
      data,
      ...options
    });
  },
  post(url, data = {}, options = {}) {
    return request({
      url,
      method: 'POST',
      data,
      ...options
    });
  },
  put(url, data = {}, options = {}) {
    return request({
      url,
      method: 'PUT',
      data,
      ...options
    });
  },
  delete(url, data = {}, options = {}) {
    return request({
      url,
      method: 'DELETE',
      data,
      ...options
    });
  }
};