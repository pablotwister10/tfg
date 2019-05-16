import argparse
import os

parser = argparse.ArgumentParser()

# Arguments
parser.add_argument('-p', '--path', help='Path')
parser.add_argument('-var', '--variables', action='append', help='Variable')
parser.add_argument('-obj', '--objectives', action='append', help='Objective Variable')

args = parser.parse_args()

'''
Takes macro_template.bas using argument parser and saves it as macro.bas
'''
def main():
    current_path = os.path.dirname(os.path.realpath(__file__))
    macro_template = args.path + '\\macro_template.bas'
    macro = open(args.path + '\\macro.bas', 'w')

    # Reading all the lines in the file
    with open(macro_template) as f:
        content = f.readlines()
    # you may also want to remove whitespace characters like `\n` at the end of each line
    # content = [x.strip() for x in content]
    # print(content)

    # print('\n')

    # Start writing every line and writes new lines with new variables and new objectives
    vars_to_delete = []
    store_param_with_vars_to_delete = []
    for line in content:
        # Writing new variables
        if line.startswith('\'Define Variables'):
            macro.writelines(line)
            macro.writelines(write_variables(args.variables))

        # Writing new objective function files
        elif line.startswith('Solver.Start'):
            macro.writelines(line)
            macro.writelines(write_objectives(args.objectives, args.path))
            # Writing last lines and exiting
            macro.writelines('\nSaveAs \"' + args.path + '\\TsalidasArriba.cst\", False\n')
            macro.writelines('\n\nEnd Sub\n')
            break

        # Deleting declaration for old variables
        elif line.startswith('Dim '):
            # Storing name of var to delete
            vars_to_delete.append(line[4:(line.__len__()-11)])
            # Storing store parameter for var to delete
            store_param_with_vars_to_delete.append('StoreParameter("' + line[4:(line.__len__()-11)] + '", '
                                                   + line[4:(line.__len__()-11)] + ')\n')
            print('Deleting: ' + line + '\tfor variable: ' + vars_to_delete[0])

        # Deleting assignation for old variables
        elif vars_to_delete.__len__() > 0:
            i = 0
            for var in vars_to_delete:
                if line.startswith(var):
                    print('Deleting assignation for variable: ' + vars_to_delete.pop(i))
                    break
                i += 1
            del i

        # Deleting store parameter for old variables
        elif store_param_with_vars_to_delete.__len__() > 0:
            i = 0
            for var in store_param_with_vars_to_delete:
                if line.startswith(var):
                    print('Deleting store parameter: ' + line[0:(line.__len__()-1)])
                    store_param_with_vars_to_delete.pop(i)
                    break
                i += 1
            del i

        # Writing default lines
        else:
            macro.writelines(line)

    # print(write_variables(args.variables))
    # print(write_objectives(args.objectives, args.path))


# Writing all vars
def write_variables(vars):
    lines = ""
    for var in vars:
        lines += write_var(var)

    return lines


# Writing lines for one var
def write_var(var):
    declaration = 'Dim ' + var + ' As Double\n'
    assignment = var + '=0\n'
    storing = 'StoreParameter("' + var + '", ' + var + ')\n\n'

    return declaration + assignment + storing


# Writing all objectives
def write_objectives(objectives, path):
    lines = ""
    for obj in objectives:
        lines += write_obj(obj, path)

    return lines


# Writing lines for one var
def write_obj(obj, path):
    tree_item = 'SelectTreeItem("1D Results\S-Parameters\\' + obj + '")\n'
    ascii_export = 'With ASCIIExport\n'
    reset = '\t.Reset()\n'
    file_name = '\t.FileName("' + path + "\Results_" + obj + '.txt")\n'
    mode = '\t.Mode("FixedNumber")\n'
    execute = '\t.Execute()\n'
    end_with = 'End With\n'

    return '\n' + tree_item + '\n' + ascii_export + reset + file_name + mode + execute + end_with + '\n'


if __name__ == '__main__':
    main()
