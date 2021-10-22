<template>
  <div>
    <footer class="footer-section">
      <div class="container">
        <div class="footer-content pt-5 pb-5">
          <div class="row">
            <div class="col-xl-4 col-lg-4 mb-50">
              <div class="footer-widget" :style="{ 'margin-top' : '-15px'}">
                <div class="footer-logo">
                  <a href="index.html"
                    ><img
                      src="../assets/logo_footer.jpg"
                      class="img-fluid"
                      alt="logo"
                  /></a>
                </div>
                <div class="footer-text">
                  <p>
                    Phần mềm quản lý sản xuất ngành gỗ, nội thất theo yêu cầu
                    khắc phục hoàn toàn các vấn đề doanh nghiệp đang mắc phải,
                    phân quyền đảm bảo tính bảo mật thông tin. Tự động xuất báo
                    cáo theo dữ liệu thực.
                  </p>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-lg-4 col-md-6 mb-30">
              <div class="footer-widget">
                <div class="footer-widget-heading">
                  <h3>Liên kết hữu ích</h3>
                </div>
                <ul>
                  <li><a href="#">Trang Chủ</a></li>
                  <li><a href="#">Thông Tin</a></li>
                  <li><a href="#">Dịch Vụ</a></li>
                  <li><a href="#">Đầu Tư</a></li>
                  <li><a href="#">Liên Lạc</a></li>
                  <li><a href="#">Giới thiệu</a></li>
                  <li><a href="#">Our Services</a></li>
                  <li><a href="#">Nhóm Chuyên Gia</a></li>
                  <li><a href="#">Liên Hệ Chúng Tôi</a></li>
                  <li><a href="#">Tin Mới Nhất</a></li>
                </ul>
              </div>
            </div>
            <div class="col-xl-4 col-lg-4 col-md-6 mb-30">
              <div class="footer-widget">
                <div class="footer-widget-heading">
                  <h3>Liên Lạc</h3>
                </div>
                <ul>
                  <li>Địa chỉ: Khu 84, Thạch Thất, Hà Nội</li>
                  <li></li>
                  <li>Số điện thoại: 0965340219</li>
                  <li></li>
                  <li>E-mail: acf@info.com</li>
                  <li></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="copyright-area">
        <div class="container">
          <div class="row">
            <div class="col-xl-6 col-lg-6 text-center text-lg-left">
              <div class="copyright-text">
                <p>
                 Copyright © 2010-2021 ACF Company. All rights reserved
                </p>
              </div>
            </div>
            <div class="col-xl-6 col-lg-6 d-none d-lg-block text-right">
              <div class="footer-menu">
                <ul>
                  <li><a href="#">Trang chủ</a></li>
                  <li><a href="#">Điều kiện</a></li>
                  <li><a href="#">Riêng tư</a></li>
                  <li><a href="#">Chính sách</a></li>
                  <li><a href="#">Liên hệ</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>
<style scoped>
ul {
  margin: 0px;
  padding: 0px;
}
.footer-section {
  background: #151414;
  position: relative;
}
.footer-logo {
  margin-bottom: 30px;
}
.footer-logo img {
  max-width: 350px;
}
.footer-text p {
  overflow: auto;
  width: 352px;
  text-align: justify;
  text-justify: distribute;
  margin-bottom: 14px;
  font-size: 14px;
  color: #bebdbd;
  line-height: 28px;
}
.footer-widget-heading h3 {
  color: rgb(255, 255, 255);
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 40px;
  position: relative;
}
.footer-widget-heading h3::before {
  content: "";
  position: absolute;
  left: 0;
  bottom: -15px;
  height: 2px;
  width: 60px;
  background: #A57E37;
}
.footer-widget ul li {
  display: inline-block;
  width: 50%;
  margin-bottom: 12px;
}
.footer-widget ul li a:hover {
  color: #A57E37;
}
.footer-widget ul li a {
  color: #bebdbd;
  text-transform: capitalize;
}
.footer-widget ul li {
  color: #bebdbd;
  font-size: 12px;
  text-transform: capitalize;
}
.copyright-area {
  background: #202020;
  padding: 25px 0;
}
.copyright-text p {
  margin: 0;
  font-size: 14px;
  color: #878787;
}
.copyright-text p a {
  color: #A57E37;
}
.footer-menu li {
  display: inline-block;
  margin-left: 20px;
}
.footer-menu li:hover a {
  color: #A57E37;
}
.footer-menu li a {
  font-size: 14px;
  color: #878787;
}
</style>
