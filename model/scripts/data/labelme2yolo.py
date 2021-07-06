import json
import argparse
import sys
import os
import shutil

from tqdm import tqdm


def parseArguments(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--json_dir",
        default="./data/raw/labels",
        type=str,
        help="the path of the json folder",
    )
    parser.add_argument(
        "--yolo_dir",
        default="./data/yolo/labels",
        type=str,
        help="the path of the json folder",
    )
    return parser.parse_args(argv)


def main(args):
    id_list = make_id_list(args.json_dir)
    for json_file in tqdm(os.listdir(args.json_dir)):
        if json_file.split(".")[-1] == "json":
            json_path = os.path.join(args.json_dir, json_file)
            data = json.load(open(json_path))
            ports_list = []
            for i in range(len(data["shapes"])):
                ports_location = data["shapes"][i]["points"]
                ports_list.append(ports_location)
            for i in range(len(data["shapes"])):
                x_lists = []
                y_lists = []
                obj_ports = ports_list[i]
                for port in obj_ports:
                    x_lists.append(port[0])
                    y_lists.append(port[1])
                x_max = sorted(x_lists)[-1]
                x_min = sorted(x_lists)[0]
                y_max = sorted(y_lists)[-1]
                y_min = sorted(y_lists)[0]

                width = x_max - x_min
                height = y_max - y_min

                u, v = data["imageHeight"], data["imageWidth"]

                center_x, center_y = (
                    round(float((x_min + width / 2.0) / v), 6),
                    round(float((y_min + height / 2.0) / u), 6),
                )

                f_width, f_height = (
                    round(float(width / v), 6),
                    round(float(height / u), 6),
                )

                label_id = str(data["shapes"][i]["label"])

                save_yolo_file(
                    label_id,
                    str(center_x),
                    str(center_y),
                    str(f_width),
                    str(f_height),
                    args.json_dir,
                    json_path,
                    args.yolo_dir,
                )

        else:
            pass

    for file in os.listdir(args.json_dir):

        b_name = ["png", "jpg"]

        dirs_path = args.yolo_dir
        os.makedirs(dirs_path, exist_ok=True)

        if not os.path.exists(
            os.path.join(args.json_dir, file.split(".")[0] + "." + "json")
        ):

            file_name = os.path.join(dirs_path, file.split(".")[0] + ".txt")
            with open(file_name, "a+") as f:
                pass
        if file.split(".")[-1] in b_name:
            shutil.copyfile(
                os.path.join(args.json_dir, file), os.path.join(dirs_path, file)
            )

    get_train_or_val(args.json_dir)
    get_label_id(args.json_dir, id_list)
    print("save done!")


def make_id_list(src_path):
    json_list = []
    id_list = []
    for path in os.listdir(src_path):
        if path.split(".")[-1] == "json":
            json_list.append(os.path.join(src_path, path))
        else:
            pass
    for json_path in json_list:
        data = json.load(open(json_path))
        for i in range(len(data["shapes"])):
            label = data["shapes"][i]["label"]
            id_list.append(label)
    id_list = list(set(id_list))

    index = range(len(id_list))

    id_dict = dict(zip(id_list, index))

    return id_dict


def save_yolo_file(id_name, x, y, w, h, path, json_path, yolo_path):
    dir_path = yolo_path
    # pdb.set_trace()
    os.makedirs(dir_path, exist_ok=True)

    txt_path = os.path.join(
        dir_path, os.path.basename(json_path).split(".")[0] + ".txt"
    )

    with open(txt_path, "a+") as f:
        f.write(id_name + " " + x + " " + y + " " + w + " " + h + "\n")

    return 0


def get_train_or_val(path):
    Txt_path = os.path.join(
        os.path.abspath(os.path.join(path, "../")) + "/yolo_train.txt"
    )
    for file_name in os.listdir(path):
        if file_name.split(".")[-1] == "jpg" or file_name.split(".")[-1] == "png":
            img_path = os.path.join(path, file_name)
            with open(Txt_path, "a+") as f:
                f.write(img_path + "\n")


def get_label_id(path, id_list):
    Txt_path = os.path.join(os.path.abspath(os.path.join(path, "../")) + "/names.txt")
    with open(Txt_path, "w") as f:
        atu = sorted(id_list.items(), key=lambda x: x[1], reverse=False)
        for ind in range(len(atu)):
            alist = list(atu[ind])
            f.write(alist[0] + "\n")


if __name__ == "__main__":
    main(parseArguments(sys.argv[1:]))
