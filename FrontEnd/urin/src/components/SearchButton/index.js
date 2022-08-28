import React, { useState } from "react";
import { css } from "@emotion/react";
import { Button } from "@mui/material";

const SearchButton = ({ id, contents, getHashtagCode, hashtags }) => {
  const SelectedButtonStyle = css`
    background-color: #0037fa;
    color: white;
    font-size: 14px;
    border-radius: 20px;
    padding: 7px;
    text-align: center;
    border-color: #0037fa;
    margin: 5px 0 5px 0;
    &:hover {
      color: white;
      background-color: rgba(0, 55, 250, 0.4);
      border-color: #0037fa;
      margin: 5px 0 5px 0;
    }
  `;

  const unselectedButtonStyle = css`
    background-color: rgba(255, 255, 255, 1);
    color: black;

    font-size: 14px;
    border-radius: 20px;
    padding: 7px;
    text-align: center;
    border-color: rgba(0, 0, 0, 0.4);
    margin: 5px 0 5px 0;

    &:hover {
      color: white;
      background-color: rgba(0, 55, 250, 1);
      border-color: rgba(0, 0, 0, 0.4);
      margin: 5px 0 5px 0;
    }
  `;

  const [selected, setSelected] = useState(false);

  const sendHashtagCode = () => {
    getHashtagCode(id);
  };

  const handleClick = (e) => {
    if (selected) {
      setSelected(false);
    } else if (hashtags.length < 3) {
      setSelected(true);
    }
    sendHashtagCode();
  };

  return (
    <div sx={{ margin: "0px" }}>
      <Button
        id={id}
        variant="outlined"
        onClick={handleClick}
        sx={selected ? SelectedButtonStyle : unselectedButtonStyle}
      >
        {contents}
      </Button>
    </div>
  );
};

export default SearchButton;
