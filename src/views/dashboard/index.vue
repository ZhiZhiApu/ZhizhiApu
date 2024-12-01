<template>
  <div class="dashboard-container">
     <div class="dashboard-text">欢迎！ {{ name }}</div>
     <div class="header-text">火影忍者</div>
    <el-carousel
      class="carousel"
      height="50vh"
      autoplay
      interval="3000"
      arrow="always"
    >
      <el-carousel-item v-for="(item, index) in banners" :key="index">
        <img :src="item.image" class="carousel-image" alt="Banner Image" />
      </el-carousel-item>
    </el-carousel>
    <div class="product-list">
      <div class="product-item" v-for="(product, index) in products" :key="index">
        <img :src="product.image" alt="Product Image" class="product-image" />
        <div class="product-info">
          <h3 class="product-title">{{ product.title }}</h3>
          <p class="product-description">{{ product.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'Dashboard',
  data() {
    return {
      banners: [
        { image: require('@/assets/images/b1.jpg') },
        { image: require('@/assets/images/b2.jpg') },
        { image: require('@/assets/images/b3.jpg') }
      ],
      products: [
        {
          image: require('@/assets/images/1.jpg'),
          title: '漩涡鸣人',
          description: '有着金色短发，蔚蓝双瞳（于后来剧情透露，两项特征皆遗传自波风水门），面有须状纹是其特征，虽为木叶隐村，但是漩涡一族的，句子的结尾通常会加个“的啦”（てばよ）。由于九尾袭击木叶，已故第四代火影把九尾的阳性查克拉封印在当时还是个婴儿的鸣人体内。'
        },
        {
          image: require('@/assets/images/2.jpg'),
          title: '波风水门',
          description: '水门和鸣人有很多相似的地方，比如两人都有金色的头发、蓝色的眼睛，使用同样的忍术（螺旋丸），自来也在将鸣人推下悬崖以激发他体内九尾的力量之前也曾说：“原谅我吧，四代”，晓中的宇智波鼬更称鸣人为第四代火影的遗物。'
        },
        {
          image: require('@/assets/images/3.jpg'),
          title: '宇智波佐助',
          description: '年幼时因目睹宇智波一族被哥哥宇智波鼬所歼灭，从而走上复仇之路。在终结之谷与漩涡鸣人展开激战，将鸣人打败后叛离木叶前去追随大蛇丸。三年后，佐助将大蛇丸吸收，并成功赢得了兄弟之战，但事后从带土口中得知了鼬的灭族真相，于是决定摧毁木叶。'
        },
        {
          image: require('@/assets/images/4.jpg'),
          title: '宇智波鼬',
          description: '火之国木叶隐村宇智波一族的天才忍者，宇智波佐助的哥哥。年幼时他与宇智波止水是挚友，实力强大，擅长使用幻术。为了保护村子免受战乱，同时为了宇智波一族的荣耀之名，被迫接受了木叶高层志村团藏下令的灭族任务，留下了弟弟佐助并刺激他向自己复仇，'
        },
        {
          image: require('@/assets/images/5.jpg'),
          title: '自来也',
          description: '自来也是火之国木叶隐村的“三忍”之一，猿飞日斩的弟子，波风水门、长门、弥彦、小南、漩涡鸣人的师父，被鸣人视为一生中最敬重的人。他指导鸣人修行，在鸣人成长的道路上起到重要作用。后到雨忍村刺探情报，结果不幸死于“晓”首领佩恩之手。'
        },
      ]
    };
  },
  computed: {
    ...mapGetters(['name'])
  }
};
</script>

<style lang="scss" scoped>
.dashboard-container {
  height: 100vh; /* 设置高度为视窗高度 */
  overflow-y: auto; /* 允许纵向滚动 */
  padding: 30px; /* 增加内边距 */
  box-sizing: border-box; /* 确保内边距不影响总高度 */
}

.header-text {
  font-size: 150px; /* 字体大小 */
  font-weight: bold;
  text-align: center;
  margin: 20px 0; /* 上下边距 */
  color: rgb(255, 81, 0);
}

.dashboard-text {
  font-size: 25px; /* 调整字体大小 */
  font-weight: bold;
  text-align: left; /* 居中对齐 */
  width: 100%; /* 确保占满父容器 */
}



.carousel {
  margin-bottom: 50px; /* 与商品列表之间的间距 */
}

.carousel-image {
  width: 100%;
  height: 100%; /* 使高度占满容器 */
  object-fit: cover; /* 按比例填充，可能裁切 */
  border-radius: 8px;
}

.product-list {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px; /* 每个商品之间的间距 */
}

.product-item {
  width: 15%; /* 调整为15%，可以展示更多商品 */
  border: 1px solid #ddd;
  border-radius: 1px;
  padding: 1px;
  text-align: center;
  background-color: #fff;
  transition: transform 0.2s ease; /* 鼠标悬停时的缩放效果 */
}


.product-item:hover {
  transform: scale(1.05);
}

.product-image {
  width: 100%;
  height: 100%; /* 确保高度适应容器 */
  object-fit: contain; /* 让图片完整显示 */
  border-radius: 4px;
  margin-bottom: 10px;
}


.carousel-image {
  width: 100%;
  height: 100%; /* 确保高度与容器一致 */
  object-fit: contain; /* 让图片完整显示 */
  border-radius: 8px;
}


.product-info {
  .product-title {
    font-size: 18px;
    margin-bottom: 5px;
    font-weight: bold;
  }
  .product-description {
    font-size: 14px;
    color: #666;
  }
}


</style>