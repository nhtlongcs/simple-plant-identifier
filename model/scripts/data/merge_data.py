from pathlib import Path
from tqdm import tqdm
import argparse
import shutil
from tqdm import tqdm
import json

a = argparse.ArgumentParser()
a.add_argument("--pathIn", nargs="+", help="path to video folder")
a.add_argument("--pathOut", help="path to images folder")
a.add_argument(
    "--labelme", default=False, action="store_true", help="path to images folder"
)


if __name__ == "__main__":

    count = 0
    args = a.parse_args()
    images_folder_name = "images"
    labels_folder_name = "labels"
    data_dirs = [Path(item) for item in args.pathIn]
    save_dir = Path(args.pathOut)

    imgs_save_dir = save_dir / images_folder_name
    lbls_save_dir = save_dir / labels_folder_name

    imgs_save_dir.mkdir(parents=True, exist_ok=True)
    lbls_save_dir.mkdir(parents=True, exist_ok=True)

    for data_dir in data_dirs:
        imgs_folder = Path(data_dir) / images_folder_name
        lbls_folder = Path(data_dir) / labels_folder_name

        lbl_paths = lbls_folder.glob("*.json")
        for lbl_path in tqdm(lbl_paths):
            filename = lbl_path.stem
            with open(str(lbl_path)) as f:
                lbl_data = f.read()
                f.close()
            lbl_data = json.loads(lbl_data)
            if args.labelme:
                lbl_data["imagePath"] = lbl_data["imagePath"].replace(
                    filename, f"{count:06}"
                )
                # Change image path in label file
            image_path = imgs_folder / (filename + ".jpg")
            shutil.copyfile(image_path, imgs_save_dir / f"{count:06}.jpg")
            with open(str(lbls_save_dir / f"{count:06}.json"), "w") as f:
                json.dump(lbl_data, f)
            count += 1
