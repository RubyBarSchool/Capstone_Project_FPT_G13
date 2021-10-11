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
        <a>{{ record.id }}{{ record.name }}</a>
      </template>
      <template slot="action" slot-scope="text, record">
        <a-button type="primary" @click="check(record)"> Check </a-button>
      </template>
    </a-table>

    <input type="file" accept="file_extension" ref="file" @change="selectImage" />
    <button
      class="btn btn-success btn-sm float-right"
      :disabled="!currentImage"
      @click="upload"
    >
      Upload
    </button>
    <div>
      <div>
        <img  src="http://localhost:8080/api/files/1.jpg" alt="dsada" />
      </div>
    </div>
   <a href="http://localhost:8080/api/files/1.jpg">anh</a>
  </div>
</template>
 <script>
import DemoAxios from "../service/demoaxios";

export default {
  name: "DemoAxios",
  data() {
    return {
      currentImage: undefined,
      previewImage: undefined,
      loading: false,
      imageUrl: "",

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
    check(record) {
      console.log(record);
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
    selectImage() {
      this.currentImage = this.$refs.file.files.item(0);
      this.previewImage = URL.createObjectURL(this.currentImage);
    },
    upload() {
      this.progress = 0;
      DemoAxios.upload(this.currentImage)
        .then((response) => {
          this.message = response.data.message;
          // return DemoAxios.getFiles();
        })
        .catch((err) => {
          this.message = "Could not upload the image! " + err;
          this.currentImage = undefined;
        });
    },
  },
};
</script>

<style>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

.preview {
  max-width: 200px;
}
</style>