<template>
  <div class="vatlieuadmin">
    <a-layout :style="{ background: 'white' }">
      <Header />
      <a-layout-content :style="{ margin: '30px 16px 0' }">
        <div
          :style="{
            minHeight: '360px',
            background: 'white',
          }"
        >
          <a-back-top>
            <div class="ant-back-top-inner">
              <font-awesome-icon
                :icon="['fas', 'angle-double-up']"
                :style="{ width: '160px', height: '50px', color: '#15AABF' }"
              />
            </div>
          </a-back-top>
          <!-- menu trên -->
          <a-input
            placeholder="Mã vật liệu"
            style="width: 150px"
            v-model="dataSearch.codeMaterial"
          />
          <!-- <a-select
            placeholder="Khung"
            mode="multiple"
            v-model="dataSearch"
            :filter-option="false"
            @search="fetchRoles"
            style="width: 150px"
          >
            <a-select-option
              v-for="(role, index) in dataRoles"
              :value="role.id"
              :key="index"
            >
              {{ role.name }}
            </a-select-option>
          </a-select> -->
          <a-button
            type="primary"
            @click="submitSearch"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'search']"
              :style="{ 'margin-right': '5px' }"
            />
            Tìm kiếm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAdd"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddUnit"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm đơn vị
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddFrame"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'file-signature']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm khung và chiều cao
          </a-button>
          <!-- menu trên -->

          <!-- table content -->
          <div :style="{ 'padding-top': '10px' }">
            <a-table
              :columns="columns"
              :data-source="dataSourceTable"
              :pagination="pagination"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="codeMaterial" slot-scope="text, record">
                {{ record.codeMaterial }}
              </template>
              <template slot="parameter" slot-scope="text, record">
                {{ record.parameter }}
              </template>
              <template slot="groupMaterial" slot-scope="text, record">
                {{ record.groupMaterial }}
              </template>
              <template slot="unitId" slot-scope="text, record">
                {{ record.unitId }}
              </template>
              <template slot="listIdCompany" slot-scope="text, record">
                {{ record.listIdCompany }}
              </template>

              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.idFrame,
                          record.idHeight,
                          record.idUnit,
                          record.price
                        )
                      "
                      :style="{ width: '44.25px' }"
                    >
                      <font-awesome-icon :icon="['fas', 'edit']" />
                    </a-button>
                  </a-col>
                  <a-col :span="9">
                    <a-popconfirm
                      v-if="dataSourceTable.length"
                      title="Bạn có chắc chắn muốn xóa không?"
                      @confirm="deleteAccount(record.id)"
                    >
                      <a-button id="delete">
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </a-button>
                    </a-popconfirm>
                  </a-col>
                </a-row>
              </template>
            </a-table>
          </div>
          <!-- table content -->

          <!-- popup add -->
          <a-modal v-model="visibleAdd" title="Thêm vật liệu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary"> Lưu </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <template v-for="(tag, index) in tags">
                  <a-tooltip v-if="tag.length > 20" :key="tag" :title="tag">
                    <a-tag
                      :key="tag"
                      :closable="index !== 0"
                      @close="() => handleClose(tag)"
                    >
                      {{ `${tag.slice(0, 20)}...` }}
                    </a-tag>
                  </a-tooltip>
                  <a-tag
                    v-else
                    :key="tag"
                    :closable="index !== 0"
                    @close="() => handleClose(tag)"
                  >
                    {{ tag }}
                  </a-tag>
                </template>
                <a-input
                  v-if="inputVisible"
                  ref="input"
                  type="text"
                  size="small"
                  :style="{ width: '78px' }"
                  :value="inputValue"
                  @change="handleInputChange"
                  @blur="handleInputConfirm"
                  @keyup.enter="handleInputConfirm"
                />
                <a-tag
                  v-else
                  style="background: #fff; borderstyle: dashed"
                  @click="showInput"
                >
                  <a-icon type="plus" /> New Tag
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Khung</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddMaterial.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Chiều cao</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddMaterial.listIdHeight"
                  :filter-option="false"
                  @search="fetchHeight"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(height, index) in dataHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Nhóm vật liệu</a-col>
              <a-col flex="auto">
                <a-col flex="auto">
                  <a-select
                    mode="multiple"
                    v-model="dataAddMaterial.listName"
                    :filter-option="false"
                    @search="fetchGroupMaterial"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(group, index) in dataGroupMaterials"
                      :value="group.id"
                      :key="index"
                    >
                      {{ group.name }}
                    </a-select-option>
                  </a-select>
                </a-col>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Đơn vị đo</a-col>
              <a-col flex="auto">
                <a-col flex="auto">
                  <a-select
                    v-model="dataAddMaterial.idUnit"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(unit, index) in dataUnits"
                      :value="unit.id"
                      :key="index"
                    >
                      {{ unit.name }}
                    </a-select-option>
                  </a-select>
                </a-col>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Công ty</a-col>
              <a-col flex="auto">
                <a-select
                  v-model="dataAddMaterial.idCompany"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(company, index) in dataCompanys"
                    :value="company.id"
                    :key="index"
                  >
                    {{ company.name }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Giá thành</a-col>
              <a-col flex="auto">
                <a-input v-model="dataAddMaterial.price"
              /></a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup add frame-->
          <a-modal v-model="visibleAddFrame" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary"> Lưu </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddUnitMaterial.idMaterial"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Khung</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddMaterial.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Chiều cao</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddMaterial.listIdHeight"
                  :filter-option="false"
                  @search="fetchHeight"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(height, index) in dataHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add frame-->

          <!-- popup add unit-->
          <a-modal v-model="visibleAddUnit" title="Thêm khung">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary"> Lưu </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <a-select
                  mode="multiple"
                  v-model="dataAddUnitMaterial.idMaterial"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.name }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Đơn vị đo</a-col>
              <a-col flex="auto">
                <a-col flex="auto">
                  <a-select
                    v-model="dataAddUnitMaterial.idUnit"
                    style="width: 100%"
                  >
                    <a-select-option
                      v-for="(unit, index) in dataUnits"
                      :value="unit.id"
                      :key="index"
                    >
                      {{ unit.name }}
                    </a-select-option>
                  </a-select>
                </a-col>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add unit-->
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import companyService from "@/service/companyService.js";
import unitService from "@/service/unitService.js";
import groupMaterialService from "@/service/groupMaterialService.js";
import frameAdminService from "@/service/frameAdminService.js";
import chieuCaoService from "@/service/chieuCaoService.js";
import vatLieuAdminService from "@/service/vatLieuAdminService.js";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "VatLieuAdmin",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
      dataSearch: {
        codeMaterial: "",
        frame: "",
        listGroupID: [],
        listIdCompany: [],
        listUnitId: [],
        pageIndex: 1,
        pageSize: 10,
        total: 0,
      },
      dataSourceTable: [],
      dataFrames: [],
      dataFrame: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataHeights: [],
      dataHeight: {
        frameHeight: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataGroupMaterials: [],
      dataGroupMaterial: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataUnits: "",
      dataUnit: {
        name: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataCompanys: "",
      dataCompany: {
        address: "",
        name: "",
        phone: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataAddMaterial: {
        idCompany: "",
        idGroup: "",
        idUnit: "",
        listIdFrame: [],
        listIdHeight: [],
        listName: [],
        price: "",
      },
      dataAddFrameHeightMaterial: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
        price: "",
      },
      dataAddUnitMaterial: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
        price: "",
      },
      dataEdit: {
        id: "",
        idFrame: "",
        idHeight: "",
        idUnit: "",
        price: "",
      },
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Mã vật liệu",
          dataIndex: "codeMaterial",
          key: "codeMaterial",
          width: 150,
          scopedSlots: { customRender: "codeMaterial" },
        },
        {
          title: "Thông số",
          dataIndex: "parameter",
          key: "parameter",
          width: 150,
          scopedSlots: { customRender: "parameter" },
        },
        {
          title: "Nhóm vật liệu",
          dataIndex: "groupMaterial",
          key: "groupMaterial",
          width: 150,
          scopedSlots: { customRender: "groupMaterial" },
        },
        {
          title: "Đơn vị đo",
          dataIndex: "unit",
          key: "unit",
          width: 150,
          scopedSlots: { customRender: "unit" },
        },
        {
          title: "Công ty",
          dataIndex: "company",
          key: "company",
          width: 150,
          scopedSlots: { customRender: "company" },
        },
        {
          title: "Giá",
          dataIndex: "price",
          key: "price",
          width: 150,
          scopedSlots: { customRender: "price" },
        },
        {
          title: "",
          dataIndex: "action",
          key: "action",
          fixed: "right",
          width: 150,
          scopedSlots: { customRender: "action" },
        },
      ],
      visibleAdd: false,
      visibleAddUnit: false,
      visibleAddFrame: false,
      tags: ["Unremovable", "Tag 2"],
      inputVisible: false,
      inputValue: "",
    };
  },
  computed: {},
  created() {
    this.submitSearch();
    this.getAllFrame();
    this.getAllHeight();
    this.getAllGroupMaterial();
    this.getAllUnit();
    this.getAllCompany();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      vatLieuAdminService
        .searchMaterial(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    submitSearch() {
      this.dataSearch.total = 0;
      vatLieuAdminService
        .searchMaterial(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    showModalAdd() {
      this.visibleAdd = true;
    },
    showModalAddUnit() {
      this.visibleAddUnit = true;
    },
    showModalAddFrame() {
      this.visibleAddFrame = true;
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleAddUnit = false;
      this.visibleAddFrame = false;
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
    getAllFrame() {
      frameAdminService
        .searchFrame(this.dataFrame)
        .then((response) => {
          this.dataFrames = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchFrame(value) {
      this.dataFrame.name = value;
      this.getAllFrame();
    },
    getAllHeight() {
      chieuCaoService
        .getAllFrameHeight(this.dataHeight)
        .then((response) => {
          this.dataHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchHeight(value) {
      this.dataHeight.frameHeight = value;
      this.getAllHeight();
    },
    getAllGroupMaterial() {
      groupMaterialService
        .getAllGroupMaterial(this.dataGroupMaterial)
        .then((response) => {
          this.dataGroupMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchGroupMaterial(value) {
      this.dataGroupMaterial.name = value;
      this.getAllGroupMaterial();
    },
    getAllUnit() {
      unitService
        .getAllUnits(this.dataUnit)
        .then((response) => {
          this.dataUnits = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllCompany() {
      companyService
        .searchCompany(this.dataCompany)
        .then((response) => {
          this.dataCompanys = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleClose(removedTag) {
      const tags = this.tags.filter((tag) => tag !== removedTag);
      console.log(tags);
      this.tags = tags;
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick(function () {
        this.$refs.input.focus();
      });
    },
    handleInputChange(e) {
      this.inputValue = e.target.value;
    },
    handleInputConfirm() {
      const inputValue = this.inputValue;
      let tags = this.tags;
      if (inputValue && tags.indexOf(inputValue) === -1) {
        tags = [...tags, inputValue];
      }
      console.log(tags);
      Object.assign(this, {
        tags,
        inputVisible: false,
        inputValue: "",
      });
    },
  },
};
</script>

<style scoped>
/* back top */
.ant-back-top-inner {
  color: rgb(241, 237, 237);
  text-align: center;
}

/* button icon */
#delete {
  background-color: rgb(255, 0, 0);
  color: white;
}
#delete:hover {
  background-color: rgba(233, 15, 15, 0.863);
  color: white;
}
#edit {
  background-color: rgb(10, 208, 243);
  color: white;
}
#edit:hover {
  background-color: rgb(0, 181, 253);
  color: white;
}
</style>