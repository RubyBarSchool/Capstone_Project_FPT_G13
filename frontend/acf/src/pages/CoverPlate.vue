<template>
  <div class="admin">
    <a-layout :style="{ background: 'white' }">
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
            :style="{ width: '150px', 'margin-right': '5px' }"
            @pressEnter="submitSearch"
            v-model="dataSearch.codeMaterial"
          />
          <a-input
            placeholder="Thông số"
            :style="{ width: '150px', 'margin-right': '5px' }"
            @pressEnter="submitSearch"
            v-model="dataSearch.frame"
          />
          <a-select
            placeholder="Nhóm tấm phủ"
            mode="multiple"
            v-model="dataSearch.listGroupID"
            @change="submitSearch"
            :style="{ width: '150px', 'margin-right': '5px' }"
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
            @change="submitSearch"
             :style="{ width: '150px', 'margin-right': '5px' }"
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
            @change="submitSearch"
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
              :icon="['fas', 'plus-square']"
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
              :icon="['fas', 'plus-square']"
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
              :icon="['fas', 'plus-square']"
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
              :scroll="{ x: 1000 }"
              :rowKey="
                (record, index) => {
                  return index;
                }
              "
              @change="handleTableChange"
            >
              <template slot="image" slot-scope="text, record">
                <img
                  v-if="record.image !== null"
                  alt="example"
                  style="width: 20%"
                  :src="record.image"
                />
              </template>
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
                          record.price,
                          record.image
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
                      ok-text="Đồng ý"
                      cancel-text="Đóng"
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
              <a-button
                key="submit"
                :loading="loadingAdd"
                type="primary"
                @click="checkFormAdd"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Mã tấm phủ</a-col
              >
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
                  <font-awesome-icon :icon="['fas', 'plus']" />
                  Thêm vật liệu
                </a-tag>
                <div
                  style="color: red"
                  v-if="checkDataInputCodeCoverPlate.show"
                >
                  {{ checkDataInputCodeCoverPlate.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Thông số</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Thông số"
                  mode="multiple"
                  v-model="dataAdd.listIdFrame"
                  :filter-option="false"
                  @search="fetchFrame"
                  style="width: 100%"
                  @change="inputFrame"
                >
                  <a-select-option
                    v-for="(frame, index) in listFrames"
                    :value="frame.id"
                    :key="index"
                  >
                    {{ frame.frame }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputNumber.show">
                  {{ checkDataInputNumber.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Chiều cao</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Chiều cao"
                  mode="multiple"
                  v-model="dataAdd.listIdHeight"
                  :filter-option="false"
                  style="width: 100%"
                  @change="inputHeight"
                >
                  <a-select-option
                    v-for="(height, index) in listFrameHeights"
                    :value="height.id"
                    :key="index"
                  >
                    {{ height.frameHeight }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputAddHeight.show">
                  {{ checkDataInputAddHeight.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Nhóm tấm phủ</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Nhóm tấm phủ"
                  v-model="dataAdd.idGroup"
                  style="width: 100%"
                  @change="inputGroup"
                >
                  <a-select-option
                    v-for="(cover, index) in listGroupCoverPlate"
                    :value="cover.id"
                    :key="index"
                  >
                    {{ cover.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputGroup.show">
                  {{ checkDataInputGroup.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Đơn vị đo</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Đơn vị đo"
                  v-model="dataAdd.idUnit"
                  style="width: 100%"
                  @change="inputUnit"
                >
                  <a-select-option
                    v-for="(unit, index) in listUnits"
                    :value="unit.id"
                    :key="index"
                  >
                    {{ unit.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputUnit.show">
                  {{ checkDataInputUnit.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Công ty</a-col
              >
              <a-col flex="auto">
                <a-select
                  placeholder="Công ty"
                  v-model="dataAdd.idCompany"
                  style="width: 100%"
                  @change="inputCompany"
                >
                  <a-select-option
                    v-for="(company, index) in listCompanys"
                    :value="company.id"
                    :key="index"
                  >
                    {{ company.name }}
                  </a-select-option>
                </a-select>
                <div style="color: red" v-if="checkDataInputCompany.show">
                  {{ checkDataInputCompany.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">
                <span style="color: red">*</span> Giá thành</a-col
              >
              <a-col flex="auto">
                <a-input-number
                  style="width: 100%"
                  :min="0"
                  @change="inputPrice"
                  v-model="dataAdd.price"
                />
                <div style="color: red" v-if="checkDataInputPrice.show">
                  {{ checkDataInputPrice.message }}
                </div></a-col
              >
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="120px">Ảnh</a-col>
              <a-col flex="auto">
                <input
                  type="file"
                  accept=".jpg, .png"
                  ref="fileupload"
                  @change="importFile($event)"
                />
              </a-col>
            </a-row>
            <a-row type="flex" v-if="showImage">
              <a-col flex="100px">
                <img
                  alt="example"
                  style="width: 50%; margin-left: auto; margin-right: auto"
                  :src="url"
                />
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup add -->

          <!-- popup edit-->
          <a-modal v-model="visibleEdit" title="Chỉnh sửa tấm phủ">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                :loading="loadingEdit"
                type="primary"
                @click="checkFormUpdate"
              >
                Lưu
              </a-button>
            </template>
            <a-form-model>
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Mã tấm phủ
                </a-col>
                <a-col flex="auto">
                  <a-input
                    placeholder="Mã tấm phủ"
                    style="width: 100%"
                    v-model="dataEditI.code"
                    disabled
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Thông số
                </a-col>
                <a-col flex="auto">
                  <a-input
                    placeholder="Thông số"
                    style="width: 100%"
                    v-model="dataEditI.parameter"
                    disabled
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Nhóm tấm phủ
                </a-col>
                <a-col flex="auto">
                  <a-input
                    placeholder="Nhóm tấm phủ"
                    style="width: 100%"
                    v-model="dataEditI.group"
                    disabled
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Đơn vị
                </a-col>
                <a-col flex="auto">
                  <a-input
                    placeholder="Đơn vị"
                    style="width: 100%"
                    v-model="dataEditI.unit"
                    disabled
                /></a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Công ty
                </a-col>
                <a-col flex="auto">
                  <a-input
                    placeholder="Công ty"
                    style="width: 100%"
                    v-model="dataEditI.company"
                    disabled
                  />
                </a-col>
              </a-row>
              <br />
              <a-row type="flex">
                <a-col flex="120px">
                  <span style="color: red">*</span> Giá thành
                </a-col>
                <a-col flex="auto">
                  <a-input-number
                    style="width: 100%"
                    :min="0"
                    v-model="dataEdit.price"
                    @change="inputEditPrice"
                /></a-col>
              </a-row>
              <div style="color: red" v-if="checkDataInputPrice.show">
                {{ checkDataInputPrice.message }}
              </div>
              <br />
            </a-form-model>
            <a-row type="flex">
              <a-col flex="120px"> Ảnh : </a-col>
              <a-col flex="auto">
                <a-form-model-item>
                  <input
                    type="file"
                    accept=".jpg, .png"
                    ref="fileupload"
                    @change="importFileEdit($event)"
                  />
                </a-form-model-item>
                <div class="row" v-if="showImage">
                  <img
                    alt="example"
                    style="width: 50%; margin-left: auto; margin-right: auto"
                    :src="url"
                  />
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup edit-->

          <!-- popup unit-->
          <a-modal v-model="visibleAddUnit" title="Thêm đơn vị">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button
                key="submit"
                type="primary"
                :loading="loadingAddUnit"
                @click="checkFormAddUnit"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Mã tấm phủ</a-col
              >
              <a-col flex="auto">
                <a-select
                  v-model="dataAddUnitCoverSheet.idMaterial"
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
                <div style="color: red" v-if="checkDataInputAddMaterial.show">
                  {{ checkDataInputAddMaterial.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Đơn vị đo</a-col
              >
              <a-col flex="auto">
                <a-select
                  v-model="dataAddUnitCoverSheet.idUnit"
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
                <div style="color: red" v-if="checkDataInputAddUnit.show">
                  {{ checkDataInputAddUnit.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
          <!-- popup unit-->

          <!-- popup khung-->
          <a-modal v-model="visibleAddHW" title="Thêm Khung và chiều cao">
            <template slot="footer">
              <a-button key="back" @click="handleCancel"> Hủy </a-button>
              <a-button key="reset" type="danger" @click="resetFrame">
                Cài lại
              </a-button>
              <a-button
                key="submit"
                type="primary"
                @click="checkFormAddFrameHeight"
                :loading="loadingFrame"
              >
                Lưu
              </a-button>
            </template>
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Mã tấm phủ</a-col
              >
              <a-col flex="auto">
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
                <div style="color: red" v-if="checkDataInputAddCoverPlate.show">
                  {{ checkDataInputAddCoverPlate.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Khung</a-col
              >
              <a-col flex="auto">
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
                <div style="color: red" v-if="checkDataInputAddFrame.show">
                  {{ checkDataInputAddFrame.message }}
                </div>
              </a-col>
            </a-row>
            <br />
            <a-row type="flex">
              <a-col flex="100px">
                <span style="color: red">*</span> Chiều cao</a-col
              >
              <a-col flex="auto">
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
                <div style="color: red" v-if="checkDataInputAddHeight.show">
                  {{ checkDataInputAddHeight.message }}
                </div>
              </a-col>
            </a-row>
          </a-modal>
        </div>
      </a-layout-content>
    </a-layout>
  </div>
</template>
 <script>
import coverSheetService from "../service/coverPlateService";
import fileService from "../service/fileService";
export default {
  name: "Admin",
  components: {},
  data() {
    return {
      url: "",
      showImage: false,
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
        image: "",
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
        image: "",
      },
      dataEditI: {
        code: "",
        group: "",
        company: "",
        id: "",
        unit: "",
        parameter: "",
        image: "",
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
          title: "Ảnh",
          dataIndex: "image",
          key: "image",
          width: 150,
          scopedSlots: { customRender: "image" },
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
          width: 180,
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
      loadingAdd: false,
      loadingEdit: false,
      checkDataInputAddMaterial: {
        show: false,
        message: "",
      },
      checkDataInputAddUnit: {
        show: false,
        message: "",
      },
      loadingAddUnit: false,
      loadingFrame: false,
      checkDataInputAddCoverPlate: {
        show: false,
        message: "",
      },
      checkDataInputAddFrame: {
        show: false,
        message: "",
      },
      checkDataInputAddHeight: {
        show: false,
        message: "",
      },
      checkDataInputCodeCoverPlate: {
        show: false,
        message: "",
      },
      checkDataInputNumber: {
        show: false,
        message: "",
      },
      checkDataInputGroup: {
        show: false,
        message: "",
      },
      checkDataInputUnit: {
        show: false,
        message: "",
      },
      checkDataInputCompany: {
        show: false,
        message: "",
      },
      checkDataInputPrice: {
        show: false,
        message: "",
      },
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
    importFileEdit(event1) {
      if (event1.target.files[0]) {
        this.dataEdit.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    importFile(event1) {
      if (event1.target.files[0]) {
        this.dataAdd.image = event1.target.files[0];
        this.url = window.URL.createObjectURL(event1.target.files[0]);
        this.showImage = true;
      }
    },
    handleTableChange(pagination) {
      this.dataSearch.pageIndex = pagination.current;
      this.pagination = pagination;
      coverSheetService
        .searchCoverSheet(this.dataSearch)
        .then((response) => {
          this.dataSourceTable = response.data.data;
          for (let i = 0; i < this.dataSourceTable.length; i++) {
            if (this.dataSourceTable[i].image !== null) {
              coverSheetService
                .preview(this.dataSourceTable[i].image)
                .then((response) => {
                  this.dataSourceTable[i].image = window.URL.createObjectURL(
                    response.data
                  );
                })
                .catch((e) => {
                  console.log(e);
                });
            }
          }

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
          for (let i = 0; i < this.dataSourceTable.length; i++) {
            if (this.dataSourceTable[i].image !== null) {
              coverSheetService
                .preview(this.dataSourceTable[i].image)
                .then((response) => {
                  this.dataSourceTable[i].image = window.URL.createObjectURL(
                    response.data
                  );
                })
                .catch((e) => {
                  console.log(e);
                });
            }
          }
          this.dataSearch.total = response.data.total;
          this.pagination.total = response.data.total;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    fetchFrame(value) {
      this.dataFrame.frame = value;
      this.getAllFrame();
    },
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
      this.dataAddUnitCoverSheet.idUnit = "";
      coverSheetService
        .getUnitByCoverSheet(id)
        .then((response) => {
          // this.submitSearch();
          if (response.data.data.length != 0) {
            this.listUnits = response.data.data;
            let type = "success";
            let message = "Thành công";
            let description = "Lấy đơn vị theo mã vật liệu thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không thành công";
            let description = "Lấy đơn vị theo mã vật liệu không thành công";
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
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy khung theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả khung theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
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
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy mã tấm phủ theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả mã tấm phủ theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
          this.listCodeCoverSheets = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getHeightsByCoverSheetAndFrame() {
      coverSheetService
        .getHeightsByCoverSheetAndFrame(this.dataForm)
        .then((response) => {
          if (response.data.data.length != 0) {
            let type = "success";
            let message = "Thành công";
            let description =
              "Lấy chiều cao theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " thành công";
            this.notifi(type, message, description);
          } else {
            let type = "error";
            let message = "Không tồn tại";
            let description =
              "Tất cả chiều cao theo " +
              this.dataForm.name1 +
              " và " +
              this.dataForm.name2 +
              " đều đã tồn tại";
            this.notifi(type, message, description);
          }
          this.listFrameHeights = response.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //edit cover plate
    inputEditPrice() {
      if (this.dataEdit.price != null && this.dataEdit.price != "") {
        this.checkDataInputPrice.show = false;
        this.checkDataInputPrice.message = "";
      } else {
        this.checkDataInputPrice.show = true;
        this.checkDataInputPrice.message = "Bạn phải điền giá thành";
      }
    },
    showModalEdit(
      id,
      code,
      idParameter,
      parameter,
      group,
      unit,
      company,
      price,
      image
    ) {
      this.checkDataInputPrice.show = false;
      this.checkDataInputPrice.message = "";
      this.dataEditI.id = id;
      this.dataEdit.idParameter = idParameter;
      this.dataEditI.unit = unit;
      this.dataEdit.price = price;

      this.dataEditI.group = group;
      this.dataEditI.company = company;
      this.dataEditI.code = code;
      this.dataEditI.parameter = parameter;
      this.getAllFrame();
      this.getAllFrameHeight();
      code = "";
      this.url = image;
      this.dataEdit.image = "";
      if (this.url != null) {
        this.showImage = true;
      } else {
        this.showImage = false;
      }
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.visibleEdit = true;
    },
    submitUpdate() {
      this.loadingEdit = true;
      this.dataEdit.price = this.dataEdit.price.trim();
      if (this.dataEdit.image != "") {
        fileService
          .uploadImage(this.dataEdit.image)
          .then((response) => {
            this.dataEdit.image = response.data.data;
            coverSheetService
              .updateCoverSheet(this.dataEdit)
              .then((response) => {
                this.submitSearch();
                if (response.data.data) {
                  let type = "success";
                  let message = "Cập nhật";
                  let description = "Cập nhật tấm phủ thành công";
                  this.notifi(type, message, description);
                } else {
                  let type = "error";
                  let message = "Cập nhật";
                  let description = "Cập nhật tấm phủ không thành công";
                  this.notifi(type, message, description);
                  coverSheetService.deleteImage(this.dataEdit.image);
                }
                this.loadingEdit = false;
                this.visibleEdit = false;
              })
              .catch(() => {
                coverSheetService.deleteImage(this.dataEdit.image);
                this.loadingEdit = false;
                this.visibleEdit = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingEdit = false;
            this.visibleEdit = false;
          });
      } else {
        this.loadingEdit = true;
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
            this.loadingEdit = false;
            this.visibleEdit = false;
          })
          .catch((e) => {
            console.log(e);
            this.loadingEdit = false;
            this.visibleEdit = false;
          });
      }
    },
    checkFormUpdate() {
      let check = true;
      if (this.dataEdit.price != null && this.dataEdit.price.trim() != "") {
        this.checkDataInputPrice.show = false;
        this.checkDataInputPrice.message = "";
      } else {
        check = false;
        this.checkDataInputPrice.show = true;
        this.checkDataInputPrice.message = "Bạn phải điền giá thành";
      }
      if (check) {
        this.submitUpdate();
      }
    },

    //edit cover plate

    //add cover plate
    showModalAdd() {
      this.checkDataInputCodeCoverPlate.show = false;
      this.checkDataInputCodeCoverPlate.message = "";
      this.checkDataInputNumber.show = false;
      this.checkDataInputNumber.message = "";
      this.checkDataInputAddHeight.show = false;
      this.checkDataInputAddHeight.message = "";
      this.checkDataInputGroup.show = false;
      this.checkDataInputGroup.message = "";
      this.checkDataInputUnit.show = false;
      this.checkDataInputUnit.message = "";
      this.checkDataInputCompany.show = false;
      this.checkDataInputCompany.message = "";
      this.checkDataInputPrice.show = false;
      this.checkDataInputPrice.message = "";
      this.getAllFrame();
      this.getAllFrameHeight();
      this.dataAdd.idCompany = "";
      this.dataAdd.idGroup = "";
      this.dataAdd.idUnit = "";
      this.dataAdd.listIdFrame = [];
      this.dataAdd.listIdHeight = [];
      this.dataAdd.listName = [];
      this.dataAdd.price = "";
      this.tags = [];
      if (this.$refs.fileupload != null) {
        this.$refs.fileupload.value = null;
      }
      this.url = "";
      this.visibleAdd = true;
      this.showImage = false;
    },
    checkFormAdd() {
      let check = true;
      if (this.tags != null && this.tags.length != 0) {
        this.checkDataInputCodeCoverPlate.show = false;
        this.checkDataInputCodeCoverPlate.message = "";
      } else {
        check = false;
        this.checkDataInputCodeCoverPlate.show = true;
        this.checkDataInputCodeCoverPlate.message = "Bạn phải điền mã vật liệu";
      }
      if (
        this.dataAdd.listIdFrame != null &&
        this.dataAdd.listIdFrame.length != 0
      ) {
        this.checkDataInputNumber.show = false;
        this.checkDataInputNumber.message = "";
      } else {
        check = false;
        this.checkDataInputNumber.show = true;
        this.checkDataInputNumber.message = "Bạn phải chọn thông số";
      }
      if (
        this.dataAdd.listIdHeight != null &&
        this.dataAdd.listIdHeight.length != 0
      ) {
        this.checkDataInputAddHeight.show = false;
        this.checkDataInputAddHeight.message = "";
      } else {
        check = false;
        this.checkDataInputAddHeight.show = true;
        this.checkDataInputAddHeight.message = "Bạn phải chọn chiều cao";
      }
      if (this.dataAdd.idGroup != null && this.dataAdd.idGroup.length != 0) {
        this.checkDataInputGroup.show = false;
        this.checkDataInputGroup.message = "";
      } else {
        check = false;
        this.checkDataInputGroup.show = true;
        this.checkDataInputGroup.message = "Bạn phải chọn nhóm tấm phủ";
      }
      if (this.dataAdd.idUnit != null && this.dataAdd.idUnit.length != 0) {
        this.checkDataInputUnit.show = false;
        this.checkDataInputUnit.message = "";
      } else {
        check = false;
        this.checkDataInputUnit.show = true;
        this.checkDataInputUnit.message = "Bạn phải chọn đơn vị đo";
      }
      if (
        this.dataAdd.idCompany != null &&
        this.dataAdd.idCompany.length != 0
      ) {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        check = false;
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải chọn công ty";
      }
      if (this.dataAdd.price != null && this.dataAdd.price != "") {
        this.checkDataInputPrice.show = false;
        this.checkDataInputPrice.message = "";
      } else {
        check = false;
        this.checkDataInputPrice.show = true;
        this.checkDataInputPrice.message = "Bạn phải điền giá thành";
      }
      if (check) {
        this.submitAdd();
      }
    },

    inputFrame() {
      if (
        this.dataAdd.listIdFrame != null &&
        this.dataAdd.listIdFrame.length != 0
      ) {
        this.checkDataInputNumber.show = false;
        this.checkDataInputNumber.message = "";
      } else {
        this.checkDataInputNumber.show = true;
        this.checkDataInputNumber.message = "Bạn phải chọn thông số";
      }
    },
    inputHeight() {
      if (
        this.dataAdd.listIdHeight != null &&
        this.dataAdd.listIdHeight.length != 0
      ) {
        this.checkDataInputAddHeight.show = false;
        this.checkDataInputAddHeight.message = "";
      } else {
        this.checkDataInputAddHeight.show = true;
        this.checkDataInputAddHeight.message = "Bạn phải chọn chiều cao";
      }
    },
    inputGroup() {
      if (this.dataAdd.idGroup != null && this.dataAdd.idGroup.length != 0) {
        this.checkDataInputGroup.show = false;
        this.checkDataInputGroup.message = "";
      } else {
        this.checkDataInputGroup.show = true;
        this.checkDataInputGroup.message = "Bạn phải chọn nhóm tấm phủ";
      }
    },
    inputUnit() {
      if (this.dataAdd.idUnit != null && this.dataAdd.idUnit.length != 0) {
        this.checkDataInputUnit.show = false;
        this.checkDataInputUnit.message = "";
      } else {
        this.checkDataInputUnit.show = true;
        this.checkDataInputUnit.message = "Bạn phải chọn đơn vị đo";
      }
    },
    inputCompany() {
      if (
        this.dataAdd.idCompany != null &&
        this.dataAdd.idCompany.length != 0
      ) {
        this.checkDataInputCompany.show = false;
        this.checkDataInputCompany.message = "";
      } else {
        this.checkDataInputCompany.show = true;
        this.checkDataInputCompany.message = "Bạn phải chọn công ty";
      }
    },
    inputPrice() {
      if (this.dataAdd.price != null && this.dataAdd.price != "") {
        this.checkDataInputPrice.show = false;
        this.checkDataInputPrice.message = "";
      } else {
        this.checkDataInputPrice.show = true;
        this.checkDataInputPrice.message = "Bạn phải điền giá thành";
      }
    },
    submitAdd() {
      this.loadingAdd = true;
      if (this.dataAdd.image != "") {
        fileService
          .uploadImage(this.dataAdd.image)
          .then((response) => {
            this.dataAdd.listName = this.tags;
            this.dataAdd.image = response.data.data;
            coverSheetService
              .addCoverSheet(this.dataAdd)
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
                  coverSheetService.deleteImage(this.dataAdd.image);
                }
                this.loadingAdd = false;
                this.visibleAdd = false;
              })
              .catch(() => {
                coverSheetService.deleteImage(this.dataAdd.image);
                this.loadingAdd = false;
                this.visibleAdd = false;
              });
          })
          .catch((e) => {
            console.log(e);
            this.loadingAdd = false;
            this.visibleAdd = false;
          });
      } else {
        this.dataAdd.listName = this.tags;
        this.loadingAdd = true;
        coverSheetService
          .addCoverSheet(this.dataAdd)
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
            this.loadingAdd = false;
            this.visibleAdd = false;
          })
          .catch((e) => {
            console.log(e);
            this.loadingAdd = false;
            this.visibleAdd = false;
          });
      }
    },
    //add cover plate

    //add unit
    handleChangeCodeSheet(value) {
      if (
        this.dataAddUnitCoverSheet.idMaterial != null &&
        this.dataAddUnitCoverSheet.idMaterial != ""
      ) {
        this.checkDataInputAddMaterial.show = false;
        this.checkDataInputAddMaterial.message = "";
      } else {
        this.checkDataInputAddMaterial.show = true;
        this.checkDataInputAddMaterial.message = "Bạn phải chọn mã tấm phủ";
      }
      this.getUnitByCoverSheet(value);
    },

    handleChangeUnit() {
      if (
        this.dataAddUnitCoverSheet.idUnit != null &&
        this.dataAddUnitCoverSheet.idUnit != ""
      ) {
        this.checkDataInputAddUnit.show = false;
        this.checkDataInputAddUnit.message = "";
      } else {
        this.checkDataInputAddUnit.show = true;
        this.checkDataInputAddUnit.message = "Bạn phải chọn đơn vị đo";
      }
    },
    showModalAddUnit() {
      this.checkDataInputAddMaterial.show = false;
      this.checkDataInputAddMaterial.message = "";
      this.checkDataInputAddUnit.show = false;
      this.checkDataInputAddUnit.message = "";
      this.dataAdd.idCompany = "";
      this.dataAdd.idGroup = "";
      this.dataAdd.idUnit = "";
      this.dataAdd.listIdFrame = [];
      this.dataAdd.listIdHeight = [];
      this.dataAdd.listName = [];
      this.dataAdd.price = [];
      this.getAllCodeCoverSheet();
      this.getAllUnit();
      this.dataAddUnitCoverSheet.idMaterial = "";
      this.dataAddUnitCoverSheet.idUnit = "";
      this.visibleAddUnit = true;
    },
    checkFormAddUnit() {
      let check = true;
      if (
        this.dataAddUnitCoverSheet.idMaterial != null &&
        this.dataAddUnitCoverSheet.idMaterial != ""
      ) {
        this.checkDataInputAddMaterial.show = false;
        this.checkDataInputAddMaterial.message = "";
      } else {
        check = false;
        this.checkDataInputAddMaterial.show = true;
        this.checkDataInputAddMaterial.message = "Bạn phải chọn mã tấm phủ";
      }
      if (
        this.dataAddUnitCoverSheet.idUnit != null &&
        this.dataAddUnitCoverSheet.idUnit != ""
      ) {
        this.checkDataInputAddUnit.show = false;
        this.checkDataInputAddUnit.message = "";
      } else {
        check = false;
        this.checkDataInputAddUnit.show = true;
        this.checkDataInputAddUnit.message = "Bạn phải chọn đơn vị đo";
      }
      if (check) {
        this.submitAddUnit();
      }
    },
    submitAddUnit() {
      this.loadingAddUnit = true;
      coverSheetService
        .addUnitCoverSheet(this.dataAddUnitCoverSheet)
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
          this.loadingAddUnit = false;
          this.visibleAddUnit = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingAddUnit = false;
          this.visibleAddUnit = false;
        });
    },
    //add unit

    //add frame
    handleChangeCodeCoverSheet() {
      if (
        this.dataAddFrameHeight.idMaterial != null &&
        this.dataAddFrameHeight.idMaterial != ""
      ) {
        this.checkDataInputAddCoverPlate.show = false;
        this.checkDataInputAddCoverPlate.message = "";
      } else {
        this.checkDataInputAddCoverPlate.show = true;
        this.checkDataInputAddCoverPlate.message = "Bạn phải chọn mã tấm phủ";
      }
      let data = {
        type: "material",
        id: this.dataAddFrameHeight.idMaterial,
        name: "",
      };
      for (let i = 0; i < this.listCodeCoverSheets.length; i++) {
        if (
          this.listCodeCoverSheets[i].id == this.dataAddFrameHeight.idMaterial
        ) {
          data.name = this.listCodeCoverSheets[i].name;
        }
      }

      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "material") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }
      this.dataSelect.push(data);
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
      if (
        this.dataAddFrameHeight.idFrame != null &&
        this.dataAddFrameHeight.idFrame != ""
      ) {
        this.checkDataInputAddFrame.show = false;
        this.checkDataInputAddFrame.message = "";
      } else {
        this.checkDataInputAddFrame.show = true;
        this.checkDataInputAddFrame.message = "Bạn phải chọn khung";
      }
      let data = {
        type: "frame",
        id: this.dataAddFrameHeight.idFrame,
        name: "",
      };
      for (let i = 0; i < this.listFrames.length; i++) {
        if (this.listFrames[i].id == this.dataAddFrameHeight.idFrame) {
          data.name = this.listFrames[i].frame;
        }
      }
      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "frame") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }
      this.dataSelect.push(data);
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
      if (
        this.dataAddFrameHeight.idHeight != null &&
        this.dataAddFrameHeight.idHeight != ""
      ) {
        this.checkDataInputAddHeight.show = false;
        this.checkDataInputAddHeight.message = "";
      } else {
        this.checkDataInputAddHeight.show = true;
        this.checkDataInputAddHeight.message = "Bạn phải chọn chiều cao";
      }
      let data = {
        type: "height",
        id: this.dataAddFrameHeight.idHeight,
        name: "",
      };
      for (let i = 0; i < this.listFrameHeights.length; i++) {
        if (this.listFrameHeights[i].id == this.dataAddFrameHeight.idHeight) {
          data.name = this.listFrameHeights[i].frameHeight;
        }
      }
      for (let i = this.dataSelect.length - 1; i >= 0; i--) {
        if (this.dataSelect[i].type == "height") {
          this.dataSelect.splice(i, 1);
          break;
        }
      }
      this.dataSelect.push(data);
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

    resetFrame() {
      this.dataSelect = [];
      this.disable = false;
      this.getAllCodeCoverSheet();
      this.getAllFrame();
      this.getAllFrameHeight();

      this.dataAddFrameHeight.idMaterial = "";
      this.dataAddFrameHeight.idFrame = "";
      this.dataAddFrameHeight.idHeight = "";

      this.checkDataInputAddCoverPlate.show = false;
      this.checkDataInputAddCoverPlate.message = "";

      this.checkDataInputAddFrame.show = false;
      this.checkDataInputAddFrame.message = "";

      this.checkDataInputAddHeight.show = false;
      this.checkDataInputAddHeight.message = "";
    },

    showModalAddHW() {
      this.resetFrame();
      this.visibleAddHW = true;
    },
    submitAddFrameHeight() {
      this.loadingFrame = true;
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
          this.loadingFrame = false;
          this.visibleAddHW = false;
        })
        .catch((e) => {
          console.log(e);
          this.loadingFrame = false;
          this.visibleAddHW = false;
        });
    },
    checkFormAddFrameHeight() {
      let check = true;
      if (
        this.dataAddFrameHeight.idMaterial != null &&
        this.dataAddFrameHeight.idMaterial != ""
      ) {
        this.checkDataInputAddCoverPlate.show = false;
        this.checkDataInputAddCoverPlate.message = "";
      } else {
        check = false;
        this.checkDataInputAddCoverPlate.show = true;
        this.checkDataInputAddCoverPlate.message = "Bạn phải chọn mã tấm phủ";
      }

      if (
        this.dataAddFrameHeight.idFrame != null &&
        this.dataAddFrameHeight.idFrame != ""
      ) {
        this.checkDataInputAddFrame.show = false;
        this.checkDataInputAddFrame.message = "";
      } else {
        check = false;
        this.checkDataInputAddFrame.show = true;
        this.checkDataInputAddFrame.message = "Bạn phải chọn khung";
      }

      if (
        this.dataAddFrameHeight.idHeight != null &&
        this.dataAddFrameHeight.idHeight != ""
      ) {
        this.checkDataInputAddHeight.show = false;
        this.checkDataInputAddHeight.message = "";
      } else {
        check = false;
        this.checkDataInputAddHeight.show = true;
        this.checkDataInputAddHeight.message = "Bạn phải chọn chiều cao";
      }
      if (check) {
        this.submitAddFrameHeight();
      }
    },
    //add frame

    handleCancel() {
      this.visibleAdd = false;
      this.visibleAddHW = false;
      this.visibleAddUnit = false;
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
      this.tags = tags;
      if (this.tags != null && this.tags.length != 0) {
        this.checkDataInputCodeCoverPlate.show = false;
        this.checkDataInputCodeCoverPlate.message = "";
      } else {
        this.checkDataInputCodeCoverPlate.show = true;
        this.checkDataInputCodeCoverPlate.message = "Bạn phải điền mã vật liệu";
      }
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
      if (inputValue.trim() != "" && tags.indexOf(inputValue) === -1) {
        tags = [...tags, inputValue.trim()];
      }
      Object.assign(this, {
        tags,
        inputVisible: false,
        inputValue: "",
      });
      if (this.tags != null && this.tags.length != 0) {
        this.checkDataInputCodeCoverPlate.show = false;
        this.checkDataInputCodeCoverPlate.message = "";
      } else {
        this.checkDataInputCodeCoverPlate.show = true;
        this.checkDataInputCodeCoverPlate.message = "Bạn phải điền mã vật liệu";
      }
    },
  },
};
</script>

<style scoped>
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