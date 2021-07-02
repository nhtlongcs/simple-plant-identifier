import cv2
import pandas as pd
import numpy as np
from pathlib import Path
import cv2

from config import label_dict


def yolo2voc(image_height, image_width, bboxes):
    """
    yolo => [xmid, ymid, w, h] (normalized)
    voc  => [x1, y1, x2, y2]
    
    """
    bboxes = bboxes.copy().astype(
        float
    )  # otherwise all value will be 0 as voc_pascal dtype is np.int

    bboxes[..., [0, 2]] = bboxes[..., [0, 2]] * image_width
    bboxes[..., [1, 3]] = bboxes[..., [1, 3]] * image_height

    bboxes[..., [0, 1]] = bboxes[..., [0, 1]] - bboxes[..., [2, 3]] / 2
    bboxes[..., [2, 3]] = bboxes[..., [0, 1]] + bboxes[..., [2, 3]]

    return bboxes


def plot_one_box(x, im, color=(128, 128, 128), label=None, line_thickness=3):
    # Plots one bounding box on image 'im' using OpenCV
    assert (
        im.data.contiguous
    ), "Image not contiguous. Apply np.ascontiguousarray(im) to plot_on_box() input image."
    tl = (
        line_thickness or round(0.002 * (im.shape[0] + im.shape[1]) / 2) + 1
    )  # line/font thickness
    c1, c2 = (int(x[0]), int(x[1])), (int(x[2]), int(x[3]))
    cv2.rectangle(im, c1, c2, color, thickness=tl, lineType=cv2.LINE_AA)
    if label:
        tf = max(tl - 1, 1)  # font thickness
        t_size = cv2.getTextSize(label, 0, fontScale=tl / 3, thickness=tf)[0]
        c2 = c1[0] + t_size[0], c1[1] - t_size[1] - 3
        cv2.rectangle(im, c1, c2, color, -1, cv2.LINE_AA)  # filled
        cv2.putText(
            im,
            label,
            (c1[0], c1[1] - 2),
            0,
            tl / 3,
            [225, 255, 255],
            thickness=tf,
            lineType=cv2.LINE_AA,
        )


def viz_one_img(im_path, annotation_path, gt_format=False, save_path=None):
    if Path(im_path).stem != Path(annotation_path).stem:
        print("Warning: im filename and annotation filename do not match, are u sure?")

    im = cv2.imread(im_path)
    h, w, _ = im.shape

    f = open(annotation_path, "r")
    data = (
        np.array(f.read().replace("\n", " ").strip().split(" "))
        .astype(np.float32)
        .reshape(-1, 5 if gt_format else 6)
    )

    lbls = data[:, 0] if gt_format else data[:, [0, 5]]
    data = data[:, [1, 2, 3, 4]]
    data = yolo2voc(h, w, data)

    for i, item in enumerate(data):
        lbl, xyxy = lbls[i], item
        lbl_txt = label_dict[lbl]
        plot_one_box(xyxy, im, label=lbl_txt, color=colors(lbl, True))
    if save_path:
        cv2.imwrite(save_path, im)


class Colors:
    # Ultralytics color palette https://ultralytics.com/
    def __init__(self):
        # hex = matplotlib.colors.TABLEAU_COLORS.values()
        hex = (
            "FF3838",
            "FF9D97",
            "FF701F",
            "FFB21D",
            "CFD231",
            "48F90A",
            "92CC17",
            "3DDB86",
            "1A9334",
            "00D4BB",
            "2C99A8",
            "00C2FF",
            "344593",
            "6473FF",
            "0018EC",
            "8438FF",
            "520085",
            "CB38FF",
            "FF95C8",
            "FF37C7",
        )
        self.palette = [self.hex2rgb("#" + c) for c in hex]
        self.n = len(self.palette)

    def __call__(self, i, bgr=False):
        c = self.palette[int(i) % self.n]
        return (c[2], c[1], c[0]) if bgr else c

    @staticmethod
    def hex2rgb(h):  # rgb order (PIL)
        return tuple(int(h[1 + i : 1 + i + 2], 16) for i in (0, 2, 4))


colors = Colors()  # create instance for 'from utils.plots import colors'


if __name__ == "__main__":
    viz_one_img(
        annotation_path="data/yolo/labels/00000.txt",
        im_path="data/raw/images/00000.jpg",
        gt_format=True,
    )

