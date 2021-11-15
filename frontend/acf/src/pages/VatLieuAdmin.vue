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
          <a-input
            placeholder="Thông số"
            style="width: 150px"
            v-model="dataSearch.listIdFrame"
          />
          <a-select
            placeholder="Nhóm vật liệu"
            mode="multiple"
            v-model="dataSearch.listGroupID"
            :filter-option="false"
            @search="fetchGroupMaterial"
            style="width: 150px"
          >
            <a-select-option
              v-for="(group, index) in dataGroupMaterials"
              :value="group.id"
              :key="index"
            >
              {{ group.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Đơn vị đo"
            v-model="dataSearch.idUnit"
            style="width: 150px"
          >
            <a-select-option
              v-for="(unit, index) in dataUnits"
              :value="unit.id"
              :key="index"
            >
              {{ unit.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Công ty"
            v-model="dataSearch.idCompany"
            style="width: 250px"
          >
            <a-select-option
              v-for="(company, index) in dataCompanys"
              :value="company.id"
              :key="index"
            >
              {{ company.name }}
            </a-select-option>
          </a-select>
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
              <template slot="name" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="parameter" slot-scope="text, record">
                {{ record.parameter }}
              </template>
              <template slot="nameGroup" slot-scope="text, record">
                {{ record.nameGroup }}
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
                      @confirm="deleteMaterial(record.id)"
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
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <template v-for="tag in tags">
                  <a-tag
                    :key="tag"
                    :closable="true"
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
                  <font-awesome-icon :icon="['fas', 'plus']" /> Thêm vật liệu
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Khung</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Thông số"
                  mode="multiple"
                  v-model="dataAddMaterial.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frameI, index) in dataFrameMaterials"
                    :value="frameI.id"
                    :key="index"
                  >
                    {{ frameI.frame }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Chiều cao</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
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
                    placeholder="Nhóm vật liệu"
                    v-model="dataAddMaterial.idGroup"
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
                    placeholder="Đơn vị đo"
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
                  placeholder="Công ty"
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

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa vật liệu">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <template v-for="tag in tags">
                  <a-tag
                    :key="tag"
                    :closable="true"
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
                  <font-awesome-icon :icon="['fas', 'plus']" /> Thêm vật liệu
                </a-tag>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Khung</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Thông số"
                  mode="multiple"
                  v-model="dataEdit.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                  disabled
                >
                  <a-select-option
                    v-for="(frameI, index) in dataFrameMaterials"
                    :value="frameI.id"
                    :key="index"
                  >
                    {{ frameI.frame }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Chiều cao</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataEdit.listIdHeight"
                  :filter-option="false"
                  @search="fetchHeight"
                  style="width: 100%"
                  disabled
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
                    placeholder="Nhóm vật liệu"
                    v-model="dataEdit.idGroup"
                    style="width: 100%"
                    disabled
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
                    placeholder="Đơn vị đo"
                    v-model="dataEdit.idUnit"
                    style="width: 100%"
                    disabled
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
                  placeholder="Công ty"
                  v-model="dataEdit.idCompany"
                  style="width: 100%"
                  disabled
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
              <a-col flex="auto"> <a-input v-model="dataEdit.price" /></a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->

          <!-- popup add unit-->
          <a-modal v-model="visibleAddUnit" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAddUnit">
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <a-select
                  v-model="dataAddUnitMaterial.idMaterial"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataSourceTable"
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
                      v-for="(unit, index) in dataUnitMaterial"
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

          <!-- popup add frame-->
          <a-modal v-model="visibleAddFrame" title="Thêm khung và chiều cao">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAddFrame">
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">Mã vật liệu</a-col>
              <a-col flex="auto">
                <a-select
                  v-model="dataAddFrameHeightMaterial.idMaterial"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in dataSourceTable"
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
                  placeholder="Thông số"
                  v-model="dataAddFrameHeightMaterial.listIdFrame"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frameI, index) in dataAllFramseMaterial"
                    :value="frameI.id"
                    :key="index"
                  >
                    {{ frameI.frame }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">Chiều cao</a-col>
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
                  v-model="dataAddFrameHeightMaterial.listIdHeight"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(height, index) in dataHeightMaterial"
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
      dataHeightMaterial: [],
      dataGroupMaterials: "",
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
      dataUnitMaterial: [],
      dataAllFramseMaterial: [],
      dataCompanys: "",
      dataCompany: {
        address: "",
        name: "",
        phone: "",
        pageIndex: 1,
        pageSize: 10,
      },
      dataFrameMaterials: [],
      dataFrameMaterial: {
        frame: "",
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
          dataIndex: "name",
          key: "name",
          width: 150,
          scopedSlots: { customRender: "name" },
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
          dataIndex: "nameGroup",
          key: "nameGroup",
          width: 150,
          scopedSlots: { customRender: "nameGroup" },
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
      visibleEdit: false,
      visibleAddUnit: false,
      visibleAddFrame: false,
      tags: [],
      inputVisible: false,
      inputValue: "",
    };
  },
  computed: {},
  created() {
    this.submitSearch();
    // this.getAllHeight();
    // this.getAllGroupMaterial();
    // this.getAllUnit();
    // this.getAllCompany();
    // this.getAllFrameMaterial();
    // this.getAllFrame();
    // this.getAllUnitsMaterialToInset();
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

    // add vật liệu
    showModalAdd() {
      this.tags = [];
      this.visibleAdd = true;
    },

    //submit add
    submitAdd() {
      this.dataAddMaterial.listName = this.tags;
      vatLieuAdminService
        .addMaterial(this.dataAddMaterial)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm vật liệu mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm vật liệu mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
    },

    //delete Material
    deleteMaterial(id) {
      vatLieuAdminService
        .deleteMaterial(id)
        .then((response) => {
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa vật liệu thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Xóa vật liệu không thành công";
            this.notifi(type, message, description);
            this.submitSearch();
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //show model Update
    showModalEdit(id, idFrame, idHeight, idUnit, price) {
      this.dataEdit.id = id;
      this.dataEdit.idFrame = idFrame;
      this.dataEdit.idHeight = idHeight;
      this.dataEdit.idUnit = idUnit;
      this.dataEdit.price = price;
      this.visibleEdit = true;
    },

    //submit update
    submitUpdate() {
      vatLieuAdminService
        .updateMaterial(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Cập nhật đơn thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Cập nhật đơn không thành công";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },

    //add đơn vị
    showModalAddUnit() {
      this.visibleAddUnit = true;
    },

    //submit add unit
    submitAddUnit() {
      vatLieuAdminService
        .addUnitMaterial(this.dataAddUnitMaterial)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm đơn vị mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm đơn vị mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAddUnit = false;
    },

    //submit add frame
    submitAddFrame() {
      vatLieuAdminService
        .addFrameHeightMaterial(this.dataAddFrameHeightMaterial)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm khung và chiều cao mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm khung và chiều cao mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAddFrame = false;
    },

    //add khung
    showModalAddFrame() {
      this.visibleAddFrame = true;
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleAddUnit = false;
      this.visibleAddFrame = false;
      this.visibleEdit = false;
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },

    //chiều cao
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
    //chiều cao

    //nhóm vật liệu
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
    //nhóm vật liệu

    //khung vật liệu
    getAllFrameMaterial() {
      vatLieuAdminService
        .getAllFrameMaterial()
        .then((response) => {
          this.dataAllFramseMaterial = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    //khung vật liệu

    //chiều cao vật liệu
    getAllFrameHeightMaterialToInset(){
      vatLieuAdminService
        .getAllFrameHeightMaterialToInset()
        .then((response) => {
          this.dataHeightMaterial = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    //chiều cao vật liệu

    //khung
    getAllFrame() {
      vatLieuAdminService
        .getAllFrame(this.dataFrameMaterial)
        .then((response) => {
          this.dataFrameMaterials = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchFrame(value) {
      this.dataFrameMaterial.frame = value;
      this.getAllFrame();
    },
    //khung

    //đơn vị
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
    //đơn vị

    //đơn vị vật liệu
    getAllUnitsMaterialToInset() {
      vatLieuAdminService
        .getAllUnitsMaterialToInset()
        .then((response) => {
          this.dataUnitMaterial = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    //đơn vị vật liệu

    //công ty
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
    //công ty

    //xử lý thẻ tag
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
    //xử lý thẻ tag
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