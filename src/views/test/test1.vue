<template>
  <div>
    <button @click="addUser">添加用户</button>
    <table>
      <thead>
        <tr>
          <th>头像</th>
          <th>用户名</th>
          <th>能力</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(user, index) in users" :key="index">
          <td style="width: 70px;">
            <div>
              <img 
                class="user-avatar" 
                :src="user.avatar" 
                v-if="user.avatar" 
                @click="toGetImg(index)"
              />
              <button 
                v-if="!user.avatar" 
                @click="toGetImg(index)"
              >
                上传头像
              </button>
            </div>
          </td>
          <td>
            <input 
              v-if="user.isEditing" 
              v-model="user.username" 
              @blur="stopEditing(index)"
            />
            <span v-else>{{ user.username }}</span>
          </td>
          <td>
            <input 
              v-if="user.isEditing" 
              v-model="user.ability" 
              @blur="stopEditing(index)"
            />
            <span v-else>{{ user.ability }}</span>
          </td>
          <td>
            <button @click="toggleEdit(index)">{{ user.isEditing ? '保存' : '修改' }}</button>
            <button @click="deleteUser(index)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
    <input type="file" ref="fileInput" style="display: none;" @change="uploadFile" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      users: [
        { avatar: '', username: '用户1', ability: '能力1', isEditing: false },
        { avatar: '', username: '用户2', ability: '能力2', isEditing: false },
      ],
      currentEditingIndex: null,
    };
  },
  methods: {
    addUser() {
      this.users.push({ avatar: '', username: '新用户', ability: '新能力', isEditing: false });
    },
    toGetImg(index) {
      this.currentEditingIndex = index;
      this.$refs.fileInput.click();
    },
    uploadFile(event) {
      const file = event.target.files[0];
      if (file && file.size / 1024 / 1024 < 2 && file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.users[this.currentEditingIndex].avatar = e.target.result;
        };
        reader.readAsDataURL(file);
      } else {
        this.$message.error('请上传小于2MB的图片文件！');
      }
    },
    toggleEdit(index) {
      this.users[index].isEditing = !this.users[index].isEditing;
      if (!this.users[index].isEditing) {
        this.$message.success('信息已保存！');
      }
    },
    stopEditing(index) {
      this.users[index].isEditing = false;
    },
    deleteUser(index) {
      this.users.splice(index, 1);
      this.$message.success('用户已删除！');
    },
  },
};
</script>

<style>
.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid #ddd;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 10px;
  border: 1px solid #ddd;
}
button {
  margin-top: 5px;
  margin-right: 5px;
}
</style>