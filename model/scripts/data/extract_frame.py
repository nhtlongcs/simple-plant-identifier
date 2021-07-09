from pathlib import Path
import cv2
import argparse
from tqdm import tqdm

a = argparse.ArgumentParser()
a.add_argument("--pathIn", help="path to video folder")
a.add_argument("--pathOut", help="path to images folder")

count = 0


def extractImages(pathIn, pathOut):
    global count
    print("start", count)
    vidcap = cv2.VideoCapture(str(pathIn))
    success, image = vidcap.read()
    success = True
    fps = vidcap.get(cv2.CAP_PROP_FPS)  # OpenCV2 version 2 used "CV_CAP_PROP_FPS"
    frame_count = int(vidcap.get(cv2.CAP_PROP_FRAME_COUNT))
    for i in tqdm(range(frame_count)):

        success, image = vidcap.read()
        if success:
            cv2.imwrite(
                str(pathOut / f"{count:05}.jpg"), image
            )  # save frame as JPEG file
            count = count + 1
    print("end", count)


if __name__ == "__main__":
    args = a.parse_args()
    data_dir = Path(args.pathIn)
    save_dir = Path(args.pathOut)
    save_dir.mkdir(parents=True, exist_ok=True)
    data_ls = data_dir.glob("*.mp4")
    data = []
    for video_path in data_ls:
        video_name = video_path.stem
        image_dir = save_dir  # / video_name
        extractImages(pathIn=video_path, pathOut=image_dir)
