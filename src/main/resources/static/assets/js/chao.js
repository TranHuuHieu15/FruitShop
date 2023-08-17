// viết một hàm để tạo ra thông báo
function
createNotification(title = "Welcome to the notification") {
    const template = `<div class="noti">
    <img src="https://source.unsplash.com/random" alt="" class="noti-image" />
    <div class="noti-content">
      <h3 class="noti-title">${title}</h3>
      <p class="noti-desc">
        Chào bạn đến với website của tôi, mong bạn vui vẻ và mua sắm nhiệt tình nhé!
      </p>
    </div>
  </div>`;
    document.body.insertAdjacentHTML("afterbegin", template);
}

const randomData = ["Welcome to FruitKha", "Today is a good day to shopping!",`Xin chào bạn, chúc bạn có một ngày mới tốt lành`];

let lastTitle;

const timer = setInterval(() => {
    let item = document.querySelector(".noti");
    if (item) item.parentNode.removeChild(item);
    //floor là hàm làm tròn xuống -> note lại cả quên
    const title = randomData[Math.floor(Math.random() * randomData.length)];
    if (lastTitle !== title) {
        createNotification(title);
    }
    lastTitle = title;
}, 1800000);
