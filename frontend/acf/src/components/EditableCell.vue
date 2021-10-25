<template>
  <div class="editable-cell">
    <div v-if="editable" class="editable-cell-input-wrapper">
      <a-row type="flex">
        <a-col flex="145px"
          ><a-input :value="value" @change="handleChange" @pressEnter="check"
        /></a-col>
        <a-col flex="auto"
          ><h3 id="check" @click="check">
            <font-awesome-icon :icon="['fas', 'check-circle']" /></h3
        ></a-col>
      </a-row>
    </div>
    <div v-else class="editable-cell-text-wrapper">
      {{ value || " " }}
      <a-button id="edit" @click="edit">
        <font-awesome-icon :icon="['fas', 'edit']" />
      </a-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditableCell",
  props: {
    text: String,
  },
  data() {
    return {
      value: this.text,
      editable: false,
    };
  },
  methods: {
    handleChange(e) {
      const value = e.target.value;
      this.value = value;
    },
    check() {
      this.editable = false;
      this.$emit("change", this.value);
    },
    edit() {
      this.editable = true;
    },
  },
};
</script>
<style scoped>
#check {
  color: rgb(24, 216, 24);
}
#edit {
  background-color: rgb(17, 196, 228);
  color: white;
}
</style>
