import {
  createAsyncThunk,
  createSlice,
  isRejectedWithValue,
} from "@reduxjs/toolkit";
import axios from "axios";

export const getMyPage = createAsyncThunk(
  "GET_MY_PAGE",
  async ({ currentAllChecked, pastAllChecked }) => {
    try {
      const response = await axios.get(
        `${process.env.REACT_APP_BACK_BASE_URL}studies/me?currentAll=${currentAllChecked}&pastAll=${pastAllChecked}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      return response.data;
    } catch (err) {
      console.log(err);
      return isRejectedWithValue(err.response.data);
    }
  }
);

const myPageSlice = createSlice({
  name: "mypage",
  initialState: {
    currentStudyList: [
      {
        id: 0,
        title: "참여 중인 스터디",
        memberCapacity: 0,
        currentMember: 0,
        status: "RECRUITING",
      },
    ],
    pastStudyList: [
      {
        id: 0,
        title: "지난 스터디",
        memberCapacity: 0,
        currentMember: 0,
        status: "TERMINATED",
      },
    ],
  },
  reducers: {},
  extraReducers: {
    [getMyPage.fulfilled]: (state, { payload }) => payload,
  },
});

export default myPageSlice.reducer;
