<template>
  <div>
    <a-table
    :columns="columns" 
      :data-source="dataSourceTable"
      :rowKey="
        (record, index) => {
          return index;
        }
      "
    >
      <template slot="name" slot-scope="text, record">
        <a>{{record.id}}{{record.name}}</a>
      </template>
      <template slot="action" slot-scope="text, record">
        <a-button type="primary" @click="check(record)">
        Check
      </a-button>
      </template>
    </a-table>
  </div>
</template>
 <script>
import DemoAxios from "../service/demoaxios";
export default {
  name: "DemoAxios",
  data() {
    return {
      dataSourceTable: [],
      columns: [
        {
          title: "ID",
          dataIndex: "id",
          key: "id",
        },
        {
          title: "Name",
          dataIndex: "name",
          key: "name",
          width: "30%",
          scopedSlots: { customRender: "name" },
        },
        
        {
          title: "Action",
          dataIndex: "action",
          key: "action",
          scopedSlots: { customRender: "action" },
        },
      ],
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    this.retrieveTutorials();
  },
  methods: {
    check(record){
      console.log(record)
    },
    retrieveTutorials() {
      let form = {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      };
      DemoAxios.getAll(form)
        .then((response) => {
          this.dataSourceTable = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style>
</style>