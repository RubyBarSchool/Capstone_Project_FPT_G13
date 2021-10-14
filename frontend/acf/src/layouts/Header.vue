<template>
  <div id="Header">
    <a-layout-header>
      <a-row type="flex">
        <a-col flex="auto"></a-col>
        <a-col flex="150px">
          <span style="margin-right: 24px">
            <a-badge :count="5"
              ><a-icon :style="{ fontSize: '30px' }" type="bell" />
            </a-badge> 
            <a-avatar :size="60" :style="{ margin: '5px' }" src="https://img.icons8.com/bubbles/100/000000/user.png" />
            <!-- <p>{{loadUser}}</p>        -->
          </span>          
        </a-col>
      </a-row>
    </a-layout-header>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user:""
    }},
    computed: {
      loadUser(){
        return JSON.parse(localStorage.getItem('user')).username;
      },
    },
};
</script>
<style scoped>
.ant-layout-header {
  background: rgb(255, 255, 255);
  padding: 0;
}
</style>
