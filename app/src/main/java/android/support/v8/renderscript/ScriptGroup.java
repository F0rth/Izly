package android.support.v8.renderscript;

import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;
import java.util.ArrayList;

public class ScriptGroup extends BaseObj {
    IO[] mInputs;
    IO[] mOutputs;

    public static final class Builder {
        private int mKernelCount;
        private ArrayList<ConnectLine> mLines = new ArrayList();
        private ArrayList<Node> mNodes = new ArrayList();
        private RenderScript mRS;
        private android.support.v8.renderscript.ScriptGroupThunker.Builder mT;

        public Builder(RenderScript renderScript) {
            if (RenderScript.isNative) {
                this.mT = new android.support.v8.renderscript.ScriptGroupThunker.Builder(renderScript);
            }
            this.mRS = renderScript;
        }

        private Node findNode(KernelID kernelID) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                Node node = (Node) this.mNodes.get(i);
                for (int i2 = 0; i2 < node.mKernels.size(); i2++) {
                    if (kernelID == node.mKernels.get(i2)) {
                        return node;
                    }
                }
            }
            return null;
        }

        private Node findNode(Script script) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                if (script == ((Node) this.mNodes.get(i)).mScript) {
                    return (Node) this.mNodes.get(i);
                }
            }
            return null;
        }

        private void mergeDAGs(int i, int i2) {
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                if (((Node) this.mNodes.get(i3)).dagNumber == i2) {
                    ((Node) this.mNodes.get(i3)).dagNumber = i;
                }
            }
        }

        private void validateCycle(Node node, Node node2) {
            for (int i = 0; i < node.mOutputs.size(); i++) {
                ConnectLine connectLine = (ConnectLine) node.mOutputs.get(i);
                if (connectLine.mToK != null) {
                    Node findNode = findNode(connectLine.mToK.mScript);
                    if (findNode.equals(node2)) {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode, node2);
                }
                if (connectLine.mToF != null) {
                    Node findNode2 = findNode(connectLine.mToF.mScript);
                    if (findNode2.equals(node2)) {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode2, node2);
                }
            }
        }

        private void validateDAG() {
            int i;
            int i2 = 0;
            for (i = 0; i < this.mNodes.size(); i++) {
                Node node = (Node) this.mNodes.get(i);
                if (node.mInputs.size() == 0) {
                    if (node.mOutputs.size() != 0 || this.mNodes.size() <= 1) {
                        validateDAGRecurse(node, i + 1);
                    } else {
                        throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                    }
                }
            }
            i = ((Node) this.mNodes.get(0)).dagNumber;
            while (i2 < this.mNodes.size()) {
                if (((Node) this.mNodes.get(i2)).dagNumber != i) {
                    throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                }
                i2++;
            }
        }

        private void validateDAGRecurse(Node node, int i) {
            if (node.dagNumber == 0 || node.dagNumber == i) {
                node.dagNumber = i;
                for (int i2 = 0; i2 < node.mOutputs.size(); i2++) {
                    ConnectLine connectLine = (ConnectLine) node.mOutputs.get(i2);
                    if (connectLine.mToK != null) {
                        validateDAGRecurse(findNode(connectLine.mToK.mScript), i);
                    }
                    if (connectLine.mToF != null) {
                        validateDAGRecurse(findNode(connectLine.mToF.mScript), i);
                    }
                }
                return;
            }
            mergeDAGs(node.dagNumber, i);
        }

        public final Builder addConnection(Type type, KernelID kernelID, FieldID fieldID) {
            if (this.mT != null) {
                this.mT.addConnection(type, kernelID, fieldID);
            } else {
                Node findNode = findNode(kernelID);
                if (findNode == null) {
                    throw new RSInvalidStateException("From script not found.");
                }
                Node findNode2 = findNode(fieldID.mScript);
                if (findNode2 == null) {
                    throw new RSInvalidStateException("To script not found.");
                }
                ConnectLine connectLine = new ConnectLine(type, kernelID, fieldID);
                this.mLines.add(new ConnectLine(type, kernelID, fieldID));
                findNode.mOutputs.add(connectLine);
                findNode2.mInputs.add(connectLine);
                validateCycle(findNode, findNode);
            }
            return this;
        }

        public final Builder addConnection(Type type, KernelID kernelID, KernelID kernelID2) {
            if (this.mT != null) {
                this.mT.addConnection(type, kernelID, kernelID2);
            } else {
                Node findNode = findNode(kernelID);
                if (findNode == null) {
                    throw new RSInvalidStateException("From script not found.");
                }
                Node findNode2 = findNode(kernelID2);
                if (findNode2 == null) {
                    throw new RSInvalidStateException("To script not found.");
                }
                ConnectLine connectLine = new ConnectLine(type, kernelID, kernelID2);
                this.mLines.add(new ConnectLine(type, kernelID, kernelID2));
                findNode.mOutputs.add(connectLine);
                findNode2.mInputs.add(connectLine);
                validateCycle(findNode, findNode);
            }
            return this;
        }

        public final Builder addKernel(KernelID kernelID) {
            if (this.mT != null) {
                this.mT.addKernel(kernelID);
            } else if (this.mLines.size() != 0) {
                throw new RSInvalidStateException("Kernels may not be added once connections exist.");
            } else if (findNode(kernelID) == null) {
                this.mKernelCount++;
                Node findNode = findNode(kernelID.mScript);
                if (findNode == null) {
                    findNode = new Node(kernelID.mScript);
                    this.mNodes.add(findNode);
                }
                findNode.mKernels.add(kernelID);
            }
            return this;
        }

        public final ScriptGroup create() {
            int i = 0;
            if (this.mT != null) {
                return this.mT.create();
            }
            if (this.mNodes.size() == 0) {
                throw new RSInvalidStateException("Empty script groups are not allowed");
            }
            int i2;
            int i3;
            for (i2 = 0; i2 < this.mNodes.size(); i2++) {
                ((Node) this.mNodes.get(i2)).dagNumber = 0;
            }
            validateDAG();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int[] iArr = new int[this.mKernelCount];
            int i4 = 0;
            int i5 = 0;
            while (i4 < this.mNodes.size()) {
                Node node = (Node) this.mNodes.get(i4);
                int i6 = i5;
                for (i3 = 0; i3 < node.mKernels.size(); i3++) {
                    int i7;
                    KernelID kernelID = (KernelID) node.mKernels.get(i3);
                    iArr[i6] = kernelID.getID(this.mRS);
                    int i8 = 0;
                    for (i7 = 0; i7 < node.mInputs.size(); i7++) {
                        if (((ConnectLine) node.mInputs.get(i7)).mToK == kernelID) {
                            i8 = 1;
                        }
                    }
                    i7 = 0;
                    int i9 = 0;
                    while (i9 < node.mOutputs.size()) {
                        int i10 = ((ConnectLine) node.mOutputs.get(i9)).mFrom == kernelID ? 1 : i7;
                        i9++;
                        i7 = i10;
                    }
                    if (i8 == 0) {
                        arrayList.add(new IO(kernelID));
                    }
                    if (i7 == 0) {
                        arrayList2.add(new IO(kernelID));
                    }
                    i6++;
                }
                i4++;
                i5 = i6;
            }
            if (i5 != this.mKernelCount) {
                throw new RSRuntimeException("Count mismatch, should not happen.");
            }
            int[] iArr2 = new int[this.mLines.size()];
            int[] iArr3 = new int[this.mLines.size()];
            int[] iArr4 = new int[this.mLines.size()];
            int[] iArr5 = new int[this.mLines.size()];
            for (i3 = 0; i3 < this.mLines.size(); i3++) {
                ConnectLine connectLine = (ConnectLine) this.mLines.get(i3);
                iArr2[i3] = connectLine.mFrom.getID(this.mRS);
                if (connectLine.mToK != null) {
                    iArr3[i3] = connectLine.mToK.getID(this.mRS);
                }
                if (connectLine.mToF != null) {
                    iArr4[i3] = connectLine.mToF.getID(this.mRS);
                }
                iArr5[i3] = connectLine.mAllocationType.getID(this.mRS);
            }
            int nScriptGroupCreate = this.mRS.nScriptGroupCreate(iArr, iArr2, iArr3, iArr4, iArr5);
            if (nScriptGroupCreate == 0) {
                throw new RSRuntimeException("Object creation error, should not happen.");
            }
            ScriptGroup scriptGroup = new ScriptGroup(nScriptGroupCreate, this.mRS);
            scriptGroup.mOutputs = new IO[arrayList2.size()];
            for (i2 = 0; i2 < arrayList2.size(); i2++) {
                scriptGroup.mOutputs[i2] = (IO) arrayList2.get(i2);
            }
            scriptGroup.mInputs = new IO[arrayList.size()];
            while (i < arrayList.size()) {
                scriptGroup.mInputs[i] = (IO) arrayList.get(i);
                i++;
            }
            return scriptGroup;
        }
    }

    static class ConnectLine {
        Type mAllocationType;
        KernelID mFrom;
        FieldID mToF;
        KernelID mToK;

        ConnectLine(Type type, KernelID kernelID, FieldID fieldID) {
            this.mFrom = kernelID;
            this.mToF = fieldID;
            this.mAllocationType = type;
        }

        ConnectLine(Type type, KernelID kernelID, KernelID kernelID2) {
            this.mFrom = kernelID;
            this.mToK = kernelID2;
            this.mAllocationType = type;
        }
    }

    static class IO {
        Allocation mAllocation;
        KernelID mKID;

        IO(KernelID kernelID) {
            this.mKID = kernelID;
        }
    }

    static class Node {
        int dagNumber;
        ArrayList<ConnectLine> mInputs = new ArrayList();
        ArrayList<KernelID> mKernels = new ArrayList();
        Node mNext;
        ArrayList<ConnectLine> mOutputs = new ArrayList();
        Script mScript;

        Node(Script script) {
            this.mScript = script;
        }
    }

    ScriptGroup(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public void execute() {
        this.mRS.nScriptGroupExecute(getID(this.mRS));
    }

    public void setInput(KernelID kernelID, Allocation allocation) {
        for (int i = 0; i < this.mInputs.length; i++) {
            if (this.mInputs[i].mKID == kernelID) {
                this.mInputs[i].mAllocation = allocation;
                this.mRS.nScriptGroupSetInput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                return;
            }
        }
        throw new RSIllegalArgumentException("Script not found");
    }

    public void setOutput(KernelID kernelID, Allocation allocation) {
        for (int i = 0; i < this.mOutputs.length; i++) {
            if (this.mOutputs[i].mKID == kernelID) {
                this.mOutputs[i].mAllocation = allocation;
                this.mRS.nScriptGroupSetOutput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                return;
            }
        }
        throw new RSIllegalArgumentException("Script not found");
    }
}
