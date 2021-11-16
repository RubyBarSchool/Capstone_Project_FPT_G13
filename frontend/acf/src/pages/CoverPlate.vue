<template>
  <div class="admin">
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
            placeholder="Mã tấm phủ"
            style="width: 150px"
            v-model="dataSearch.codeMaterial"
          />
          <a-input
            placeholder="Thông số"
            style="width: 150px"
            v-model="dataSearch.frame"
          />
          <a-select
            placeholder="Nhóm tấm phủ"
            mode="multiple"
            v-model="dataSearch.listGroupID"
            style="width: 150px"
          >
            <a-select-option
              v-for="(cover, index) in listGroupCoverPlate"
              :value="cover.id"
              :key="index"
            >
              {{ cover.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Đơn vị đo"
            mode="multiple"
            v-model="dataSearch.listUnitId"
            style="width: 150px"
          >
            <a-select-option
              v-for="(unit, index) in listUnits"
              :value="unit.id"
              :key="index"
            >
              {{ unit.name }}
            </a-select-option>
          </a-select>
          <a-select
            placeholder="Công ty"
            mode="multiple"
            v-model="dataSearch.listIdCompany"
            style="width: 150px"
          >
            <a-select-option
              v-for="(company, index) in listCompanys"
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
              :icon="['fas', 'plus']"
              :style="{ 'margin-right': '5px' }"
            />

            Thêm tấm phủ
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddUnit"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm đơn vị
          </a-button>
          <a-button
            type="primary"
            @click="showModalAddHW"
            :style="{ 'margin-left': '5px' }"
          >
            <font-awesome-icon
              :icon="['fas', 'plus']"
              :style="{ 'margin-right': '5px' }"
            />
            Thêm khung và chiều cao
          </a-button>
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
              <template slot="coverplatecode" slot-scope="text, record">
                {{ record.name }}
              </template>
              <template slot="specifications" slot-scope="text, record">
                {{ record.parameter }}
              </template>
              <template slot="unit" slot-scope="text, record">
                {{ record.unit }}
              </template>
              <template slot="company" slot-scope="text, record">
                {{ record.company }}
              </template>
              <template slot="price" slot-scope="text, record">
                {{ record.price }}
              </template>
              <template slot="action" slot-scope="text, record">
                <a-row>
                  <a-col :span="9">
                    <a-button
                      id="edit"
                      @click="
                        showModalEdit(
                          record.id,
                          record.name,
                          record.idParameter,
                          record.parameter,
                          record.nameGroup,
                          record.unit,
                          record.company,
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
                      @confirm="deleteCoverSheet(record.id)"
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

          <!-- popup add-->
          <a-modal v-model="visibleAdd" title="Thêm tấm phủ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAdd">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <div>
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
                    <a-icon type="plus" /> New Tag
                  </a-tag>
                </div>
              </a-form-model-item>
              <a-form-model-item label="Thông số">
                <a-select
                  placeholder="Thông số"
                  mode="multiple"
                  v-model="dataAdd.listIdFrame"
                  :filter-option="false"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(frame, index) in listFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.frame }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Chiều cao">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataAdd.listIdHeight"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(height, index) in listFrameHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Nhóm tấm phủ">
                <a-select
                  placeholder="Nhóm tấm phủ"
                  v-model="dataAdd.idGroup"
                  :filter-option="false"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(cover, index) in listGroupCoverPlate"
                    :value="cover.id"
                    :key="index"
                  >
                    {{ cover.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Đơn vị">
                <a-select
                  placeholder="Đơn vị"
                  v-model="dataAdd.idUnit"
                  :filter-option="false"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(unit, index) in listUnits"
                    :value="unit.id"
                    :key="index"
                  >
                    {{ unit.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Công ty">
                <a-select
                  placeholder="Công ty"
                  v-model="dataAdd.idCompany"
                  :filter-option="false"
                  style="width: 100%"
                >
                  <a-select-option
                    v-for="(company, index) in listCompanys"
                    :value="company.id"
                    :key="index"
                  >
                    {{ company.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Giá thành">
                <a-input
                  placeholder="Giá thành"
                  style="width: 100%"
                  v-model="dataAdd.price"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa tấm phủ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitUpdate">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <a-input
                  placeholder="Mã tấm phủ"
                  style="width: 100%"
                  v-model="dataEditI.code"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Thông số">
                <a-input
                  placeholder="Thông số"
                  style="width: 100%"
                  v-model="dataEditI.parameter"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Nhóm tấm phủ">
                <a-input
                  placeholder="Nhóm tấm phủ"
                  style="width: 100%"
                  v-model="dataEditI.group"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Đơn vị">
                <a-input
                  placeholder="Đơn vị"
                  style="width: 100%"
                  v-model="dataEditI.unit"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Công ty">
                <a-input
                  placeholder="Công ty"
                  style="width: 100%"
                  v-model="dataEditI.company"
                  disabled
                />
              </a-form-model-item>
              <a-form-model-item label="Giá thành">
                <a-input
                  placeholder="Giá thành"
                  style="width: 100%"
                  v-model="dataEdit.price"
                />
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup edit-->

          <!-- popup unit-->
          <a-modal v-model="visibleAddUnit" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="submit" type="primary" @click="submitAddUnit">
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <a-select
                  placeholder="Mã tấm phủ"
                  v-model="dataAddUnitCoverSheet.idMaterial"
                  :filter-option="false"
                  style="width: 100%"
                  @change="handleChangeCodeSheet"
                >
                  <a-select-option
                    v-for="(code, index) in listCodeCoverSheets"
                    :value="code.id"
                    :key="index"
                  >
                    {{ code.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Đơn vị đo">
                <a-select
                  placeholder="Đơn vị đo"
                  v-model="dataAddUnitCoverSheet.idUnit"
                  :filter-option="false"
                  style="width: 100%"
                  @change="handleChangeUnit"
                >
                  <a-select-option
                    v-for="(unit, index) in listUnits"
                    :value="unit.id"
                    :key="index"
                  >
                    {{ unit.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
          <!-- popup unit-->

          <!-- popup khung-->
          <a-modal v-model="visibleAddHW" title="Thêm Khung và chiều cao">
            <template slot="footer">
              <a-button key="reset" type="danger" @click="resetFrame">
                Reset
              </a-button>
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="submitAddFrameHeight"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-form-model-item label="Mã tấm phủ">
                <a-select
                  placeholder="Mã tấm phủ"
                  v-model="dataAddFrameHeight.idMaterial"
                  :filter-option="false"
                  style="width: 100%"
                  @change="handleChangeCodeCoverSheet"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(code, index) in listCodeCoverSheets"
                    :value="code.id"
                    :key="index"
                  >
                    {{ code.name }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Khung">
                <a-select
                  placeholder="Khung"
                  v-model="dataAddFrameHeight.idFrame"
                  style="width: 100%"
                  @change="handleChangeFrame"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(frame, index) in listFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.frame }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
              <a-form-model-item label="Chiều cao">
                <a-select
                  placeholder="Chiều cao"
                  v-model="dataAddFrameHeight.idHeight"
                  style="width: 100%"
                  @change="handleChangeHeight"
                  :disabled="disable"
                >
                  <a-select-option
                    v-for="(height, index) in listFrameHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </a-form-model>
          </a-modal>
        </div>
      </a-layout-content>
      <Footer />
    </a-layout>
  </div>
</template>
 <script>
import coverSheetService from "../service/coverPlateService";
import Header from "@/layouts/Header.vue";
import Footer from "@/layouts/Footer.vue";

export default {
  name: "Admin",
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
      // listCodeMaterial: [],
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
      dataRoles: [],
      dataEmployees: [],
      dataAdd: {
        idCompany: "",
        idGroup: "",
        idUnit: "",
        listIdFrame: [],
        listIdHeight: [],
        listName: [],
        price: "",
      },
      dataAddUnitCoverSheet: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
        price: "",
      },
      dataAddFrameHeight: {
        idFrame: "",
        idHeight: "",
        idMaterial: "",
        idUnit: "",
      },
      listGroupCoverPlate: [],
      listUnits: [],
      listCodeCoverSheets: [],
      dataEdit: {
        idParameter: "",
        price: "",
      },
      dataEditI: {
        code: "",
        group: "",
        company: "",
        id: "",
        unit: "",
        parameter: "",
      },
      listCompanys: [],
      listCompany: {
        address: "",
        name: "",
        pageIndex: 1,
        pageSize: 10,
        phone: "",
      },
      listFrames: [],
      dataFrame: {
        frame: "",
        pageIndex: 1,
        pageSize: 10,
      },
      // listFrameByCoverSheetAndHeights: [],
      dataFrameByCoverSheetAndHeight: "",
      // listCoverSheetByFrameAndHeights: [],
      dataCoverSheetByFrameAndHeight: "",
      // listHeightsByCoverSheetAndFrames: [],
      dataHeightsByCoverSheetAndFrame: "",
      dataForm: {
        id1: 0,
        id2: 0,
        name1: "",
        name2: "",
      },
      listFrameHeights: [],
      // listUnitCoverSheets: [],
      listFrameCoverSheets: [],
      listFrameHeightCoverSheets: [],
      dataSelect: [],
      columns: [
        {
          title: "STT",
          width: 100,
          dataIndex: "id",
          key: "id",
          fixed: "left",
        },
        {
          title: "Mã tấm phủ",
          dataIndex: "coverplatecode",
          key: "coverplatecode",
          width: 150,
          scopedSlots: { customRender: "coverplatecode" },
        },
        {
          title: "Thông số",
          dataIndex: "specifications",
          key: "specifications",
          width: 150,
          scopedSlots: { customRender: "specifications" },
        },

        {
          title: "Đơn vị",
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
          title: "Giá thành",
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
      visibleAddHW: false,
      visibleAddUnit: false,
      disable: false,
      checkDataInput: {
        show: false,
        message: "",
      },
      tags: [],
      inputVisible: false,
      inputValue: "",
    };
  },
  computed: {},
  created() {
    this.submitSearch();
    this.getAllGroupCoverPlate();
    this.getAllUnit();
    this.getAllCompany();
  },
  methods: {
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      coverSheetService
        .searchCoverSheet(this.dataSearch)
        .then((response) => {
          // this.listCodeMaterial = response.data.data;
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
      coverSheetService
        .searchCoverSheet(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // fetchFrame(value) {
    //   this.dataFrame.length = value;
    //   this.getAllGroupCoverPlate();
    // },
    getAllGroupCoverPlate() {
      coverSheetService
        .listGroupCoverPlate()
        .then((response) => {
          this.listGroupCoverPlate = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // fetchEmployees(value) {
    //   this.dataEmployee.name = value;
    //   this.getAllEmployeeNotAccount();
    // },
    getAllUnit() {
      coverSheetService
        .listUnit()
        .then((response) => {
          this.listUnits = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllCompany() {
      coverSheetService
        .listCompany(this.listCompany)
        .then((response) => {
          this.listCompanys = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllFrame() {
      coverSheetService
        .getAllFrame(this.dataFrame)
        .then((response) => {
          this.listFrames = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllFrameHeight() {
      coverSheetService
        .getAllFrameHeight()
        .then((response) => {
          this.listFrameHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    // getUnitCoverSheet() {
    //   coverSheetService
    //     .listUnitCoverSheet()
    //     .then((response) => {
    //       this.listUnitCoverSheets = response.data.data;
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
    getFrameCoverSheet() {
      coverSheetService
        .listFrameCoverSheet()
        .then((response) => {
          this.listFrameCoverSheets = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getFrameHeightCoverSheet() {
      coverSheetService
        .listFrameHeightCoverSheet()
        .then((response) => {
          this.listFrameHeightCoverSheets = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllCodeCoverSheet() {
      coverSheetService
        .listCodeCoverSheet()
        .then((response) => {
          this.listCodeCoverSheets = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getCoverSheetByUnit(id) {
      coverSheetService
        .getCoverSheetByUnit(id)
        .then((response) => {
          // this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "OK";
            let description = response.data.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Error";
            let description = response.data.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getUnitByCoverSheet(id) {
      coverSheetService
        .getUnitByCoverSheet(id)
        .then((response) => {
          // this.submitSearch();
          if (response.data.data) {
            this.listUnits = response.data.data;
            let type = "success";
            let message = "Ok";
            let description = response.data.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Error";
            let description = response.data.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getFrameByCoverSheetAndHeight() {
      coverSheetService
        .getFrameByCoverSheetAndHeight(this.dataForm)
        .then((response) => {
          // this.listFrameByCoverSheetAndHeights = response.data.data;
          // this.listFrames = this.listFrameByCoverSheetAndHeights;
          this.listFrames = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getCoverSheetByFrameAndHeight() {
      coverSheetService
        .getCoverSheetByFrameAndHeight(this.dataForm)
        .then((response) => {
          // this.listCoverSheetByFrameAndHeights = response.data.data;
          // this.listFrameHeights = this.listCoverSheetByFrameAndHeights;
          this.listFrameHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getHeightsByCoverSheetAndFrame() {
      coverSheetService
        .getHeightsByCoverSheetAndFrame(this.dataForm)
        .then((response) => {
          // this.listHeightsByCoverSheetAndFrames = response.data.data;
          // this.listCodeCoverSheets = this.listHeightsByCoverSheetAndFrames;
          this.listCodeCoverSheets = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleChangeCodeCoverSheet() {
      let data = {
        type: "material",
        id: this.dataAddFrameHeight.idMaterial,
        name: "",
      };
      for (var i = 0; i < this.listCodeCoverSheets.length; i++) {
        if (
          this.listCodeCoverSheets[i].id == this.dataAddFrameHeight.idMaterial
        ) {
          data.name = this.listCodeCoverSheets[i].name;
        }
      }
      this.dataSelect.push(data);
      console.log(this.dataSelect);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = data.id;
        this.dataForm.name1 = data.name;
        this.dataForm.id2 = this.dataSelect[0].id;
        this.dataForm.name2 = this.dataSelect[0].name;
        if (this.dataSelect[0].type == "frame") {
          this.getHeightsByCoverSheetAndFrame();
        } else {
          this.getCoverSheetByFrameAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },
    handleChangeFrame() {
      let data = {
        type: "frame",
        id: this.dataAddFrameHeight.idFrame,
        name: "",
      };
      for (var i = 0; i < this.listFrames.length; i++) {
        if (this.listFrames[i].id == this.dataAddFrameHeight.idFrame) {
          data.name = this.listFrames[i].name;
        }
      }
      this.dataSelect.push(data);
      console.log(this.dataSelect);
      if (this.dataSelect.length == 2) {
        if (this.dataSelect[0].type == "material") {
          this.dataForm.id1 = this.dataSelect[0].id;
          this.dataForm.name1 = this.dataSelect[0].name;
          this.dataForm.id2 = data.id;
          this.dataForm.name2 = data.name;
          this.getHeightsByCoverSheetAndFrame();
        } else {
          this.dataForm.id1 = data.id;
          this.dataForm.name1 = data.name;
          this.dataForm.id2 = this.dataSelect[0].id;
          this.dataForm.name2 = this.dataSelect[0].name;
          this.getCoverSheetByFrameAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },
    handleChangeHeight() {
      let data = {
        type: "height",
        id: this.dataAddFrameHeight.idHeight,
        name: "",
      };
      for (var i = 0; i < this.listFrameHeights.length; i++) {
        if (this.listFrameHeights[i].id == this.dataAddFrameHeight.idHeight) {
          data.name = this.listFrameHeights[i].name;
        }
      }
      this.dataSelect.push(data);
      console.log(this.dataSelect);
      if (this.dataSelect.length == 2) {
        this.dataForm.id1 = this.dataSelect[0].id;
        this.dataForm.name1 = this.dataSelect[0].name;
        this.dataForm.id2 = data.id;
        this.dataForm.name2 = data.name;

        if (this.dataSelect[0].type == "material") {
          this.getFrameByCoverSheetAndHeight();
        } else {
          this.getCoverSheetByFrameAndHeight();
        }
      }
      if (this.dataSelect.length == 3) {
        this.disable = true;
      }
    },
    handleChangeCodeSheet(value) {
      this.getUnitByCoverSheet(value);
    },
    handleChangeUnit(value) {
      this.getCoverSheetByUnit(value);
    },
    showModalAdd() {
      this.visibleAdd = true;
      // this.checkDataInput.show = false;
      // this.dataAddUnitCoverSheet.idUnit = "";
      this.getAllFrame();
      this.getAllFrameHeight();
    },
    showModalAddUnit() {
      this.getAllCodeCoverSheet();
      this.getAllUnit();
      this.dataAddUnitCoverSheet.idMaterial = "";
      this.dataAddUnitCoverSheet.idUnit = "";
      this.visibleAddUnit = true;
      // this.getUnitCoverSheet();
    },
    showModalAddHW() {
      this.visibleAddHW = true;
      // this.getFrameCoverSheet();
      // this.getFrameHeightCoverSheet();
      this.getAllCodeCoverSheet();
      this.getAllFrame();
      this.getAllFrameHeight();
      this.dataAddFrameHeight.idMaterial = "";
      this.dataAddFrameHeight.idFrame = "";
      this.dataAddFrameHeight.idHeight = "";
    },
    // checkFormAdd() {
    //   if (this.dataAdd.password != null && this.dataAdd.password != "") {
    //     this.checkDataInput.show = false;
    //     this.checkDataInput.message = "";
    //     this.submitAdd();
    //   } else {
    //     this.checkDataInput.show = true;
    //     this.checkDataInput.message = "Bạn phải điền vào chỗ trống";
    //   }
    // },
    submitAdd() {
      this.dataAdd.listName = this.tags;
      coverSheetService
        .addCoverSheet(this.dataAdd)
        .then((response) => {
          // this.dataEmployees = response.data.data;
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAdd = false;
      this.dataAdd.idCompany = "";
      this.dataAdd.idGroup = "";
      this.dataAdd.idUnit = "";
      this.dataAdd.listIdFrame = [];
      this.dataAdd.listIdHeight = [];
      this.dataAdd.listName = [];
      this.dataAdd.price = [];
    },
    resetFrame() {
      this.disable = false;
      this.dataAddFrameHeight.idMaterial="";
      this.dataAddFrameHeight.idFrame="";
      this.dataAddFrameHeight.idHeight="";
      this.getAllCodeCoverSheet();
      this.getAllFrame();
      this.getAllFrameHeight();
    },
    submitAddUnit() {
      coverSheetService
        .addUnitCoverSheet(this.dataAddUnitCoverSheet)
        .then((response) => {
          // this.dataEmployees = response.data.data;
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAddUnit = false;
      this.dataAdd.idCompany = "";
      this.dataAdd.idGroup = "";
      this.dataAdd.idUnit = "";
      this.dataAdd.listIdFrame = [];
      this.dataAdd.listIdHeight = [];
      this.dataAdd.listName = [];
      this.dataAdd.price = [];
    },
    submitAddFrameHeight() {
      coverSheetService
        .addFrameHeightCoverSheet(this.dataAddFrameHeight)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Thêm mới";
            let description = response.data.message;
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleAddHW = false;
      this.dataAdd.idCompany = "";
      this.dataAdd.idGroup = "";
      this.dataAdd.idUnit = "";
      this.dataAdd.listIdFrame = [];
      this.dataAdd.listIdHeight = [];
      this.dataAdd.listName = [];
      this.dataAdd.price = [];
    },
    handleCancel() {
      this.visibleAdd = false;
      this.visibleAddHW = false;
      this.visibleAddUnit = false;
      this.visibleEdit = false;
    },
    showModalEdit(
      id,
      code,
      idParameter,
      parameter,
      group,
      unit,
      company,
      price
    ) {
      this.dataEditI.id = id;
      this.dataEdit.idParameter = idParameter;
      this.dataEditI.unit = unit;
      this.dataEdit.price = price;

      this.dataEditI.group = group;
      this.dataEditI.company = company;
      this.dataEditI.code = code;
      this.dataEditI.parameter = parameter;
      this.visibleEdit = true;
      this.getAllFrame();
      this.getAllFrameHeight();
      code = "";
    },
    submitUpdate() {
      coverSheetService
        .updateCoverSheet(this.dataEdit)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Cập nhật";
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Cập nhật";
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
      this.visibleEdit = false;
    },

    deleteCoverSheet(id) {
      coverSheetService
        .deleteCoverSheet(id)
        .then((response) => {
          this.submitSearch();
          if (response.data.data) {
            let type = "success";
            let message = "Xóa";
            let description = "Xóa thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Xóa";
            let description = "Account đang đăng nhập không được xóa";
            this.notifi(type, message, description);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    notifi(type, message, description) {
      this.$notification[type]({
        message: message,
        description: description,
      });
    },
    //thêm tags
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
#user {
  background-color: rgb(76, 238, 12);
  color: white;
}
#user:hover {
  background-color: rgb(42, 253, 0);
  color: white;
}
</style>