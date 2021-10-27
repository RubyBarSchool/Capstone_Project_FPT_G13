<template>
  <div class="editable-cell">
    <div v-if="editable" class="editable-cell-input-wrapper">
      <a-row type="flex">
        <a-col flex="135px"
          ><a-input :value="value" @change="handleChange" @pressEnter="check"
        /></a-col>
        <a-col flex="auto"
          ><h5 id="check" @click="check" :style="{'margin-left' : '8px'}">
            <font-awesome-icon :icon="['fas', 'check']" /></h5
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
/* check */
#check {
  color: rgb(76, 238, 12);;
}
#check:hover {
  font-size: 23px;
  color: rgb(42, 253, 0);
}
/* button */
#edit {
  background-color: rgb(15, 208, 241);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
</style>
