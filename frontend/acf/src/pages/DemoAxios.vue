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
        {{record.id}}{{record.name}}
      </template>
      <template slot="action" slot-scope="text, record">
        <a-popconfirm
          v-if="dataSourceTable.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <a href="javascript:;">Delete</a>
        </a-popconfirm>
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
          title: "name",
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