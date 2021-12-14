<template>
  <div class="profile">
    <a-layout :style="{ background: 'white' }">
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top :style="{ width: '5vh', height: '15vh' }" />
          <!-- content -->

          <div class="card-container">
            <a-row type="flex">
              <a-col flex="500px">
                <div class="col-md-4">
                  <div class="card-block text-center">
                    <div class="m-b-25">
                      <img
                        src="https://img.icons8.com/bubbles/100/000000/user.png"
                        class="img-radius"
                      />
                    </div>
                    <h6>Lê Hoàng Anh</h6>
                    <p>Giám đốc</p>
                  </div>
                </div>
              </a-col>
              <a-col flex="auto">
                <a-tabs default-active-key="1" class="tab">
                  <a-tab-pane key="1" tab="Timeline">
                    <a-timeline>
                      <a-timeline-item color="green">
                        Đã nộp đơn xin nghỉ 2015-09-01
                      </a-timeline-item>
                      <a-timeline-item color="green">
                        Đã nộp đơn xin nghỉ 2015-09-01
                      </a-timeline-item>
                      <a-timeline-item color="red">
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ 2015-09-01</p>
                      </a-timeline-item>
                      <a-timeline-item>
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ 2015-09-01</p>
                      </a-timeline-item>
                      <a-timeline-item color="gray">
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ 2015-09-01</p>
                      </a-timeline-item>
                      <a-timeline-item color="gray">
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ</p>
                        <p>Đã nộp đơn xin nghỉ 2015-09-01</p>
                      </a-timeline-item>
                    </a-timeline>
                  </a-tab-pane>
                  <a-tab-pane key="2" tab="Thông tin">
                    <div class="col-md-8">
                      <div class="card-block">
                        <h5 class="m-b-20 p-b-5 b-b-default f-w-600">
                          Thông tin tài khoản
                        </h5>
                        <div class="row">
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Giới Tính :</p>
                            <h6 class="text-muted f-w-400">Nam</h6>
                          </div>
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Ngày Sinh :</p>
                            <h6 class="text-muted f-w-400">01-01-2021</h6>
                          </div>
                        </div>
                        <h6
                          class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"
                        ></h6>
                        <div class="row">
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Email :</p>
                            <h6 class="text-muted f-w-400">alh@gmail.com</h6>
                          </div>
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Điện thoại :</p>
                            <h6 class="text-muted f-w-400">0965340219</h6>
                          </div>
                        </div>
                        <h6
                          class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600"
                        ></h6>
                        <div class="row">
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Địa chỉ :</p>
                            <h6 class="text-muted f-w-400">Mỹ Đình - Hà Nội</h6>
                          </div>
                        </div>
                      </div>
                    </div>
                  </a-tab-pane>
                  <a-tab-pane key="3" tab="Thay đổi mật khẩu">
                    <h6>Nhập mật khẩu cũ :</h6>
                    <a-input-password
                      placeholder="Nhập mật khẩu cũ"
                      style="width: 500px"
                    />
                    <br />
                    <br />
                    <h6>Nhập mật khẩu mới :</h6>
                    <a-input-password
                      placeholder="Nhập mật khẩu mới"
                      style="width: 500px"
                    />
                    <br />
                    <br />
                    <h6>Nhập lại mật khẩu :</h6>
                    <a-input-password
                      placeholder="Nhập lại mật khẩu"
                      style="width: 500px"
                    />
                    <br />
                    <br />
                    <a-button type="primary"> Hủy </a-button>
                    <a-button type="primary"> Lưu </a-button>
                  </a-tab-pane>
                </a-tabs>
              </a-col>
            </a-row>
          </div>
          <!-- content -->
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
export default {
  name: "Profile",
  data() {
    return {};
  },
  methods: {
    callback(key) {
      console.log(key);
    },
  },
};
</script>

<style scoped>
.col-md-4 {
  border-radius: 10px;
  height: 70vh;
  width: 40vh;
  box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px,
    rgba(17, 17, 26, 0.1) 0px 0px 8px;
}

.tab {
    border-radius: 10px;
    height: 70vh;   
    padding-left: 30px;
    box-shadow: rgba(17, 17, 26, 0.05) 0px 1px 0px,
    rgba(17, 17, 26, 0.1) 0px 0px 8px;
}
</style>