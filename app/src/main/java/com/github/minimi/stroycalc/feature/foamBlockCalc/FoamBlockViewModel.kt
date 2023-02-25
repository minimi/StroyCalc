package com.github.minimi.stroycalc.feature.foamBlockCalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.minimi.stroycalc.models.Calculator
import kotlin.math.ceil
import kotlin.math.roundToInt

class FoamBlockViewModel : ViewModel(), Calculator<InputParameters, CalculationState> {

    val predefinedBlockSizes = listOf(
        BlockSize("600x250x500"),
        BlockSize("600x250x375"),
        BlockSize("600x250x300"),
        BlockSize("600x250x250"),
        BlockSize("600x200x500"),
        BlockSize("600x200x375"),
        BlockSize("600x200x300"),
        BlockSize("600x200x250"),
        BlockSize("600x200x150"),
        BlockSize("600x250x100"),
        BlockSize("600x250x75"),
        BlockSize("600x250x150"),
        BlockSize("600x200x100"),
        BlockSize("600x200x75"),
        BlockSize("625x250x500"),
        BlockSize("625x250x375"),
        BlockSize("625x250x300"),
        BlockSize("625x250x250"),
        BlockSize("625x200x500"),
        BlockSize("625x200x375"),
        BlockSize("625x200x300"),
        BlockSize("625x200x250"),
        BlockSize("625x200x150"),
        BlockSize("625x250x100"),
        BlockSize("625x250x75"),
        BlockSize("625x250x150"),
        BlockSize("625x200x100"),
        BlockSize("625x200x75"),
        BlockSize("625x200x400"),
        BlockSize("625x250x400"),
    )

    override var result by mutableStateOf<CalculationState>(CalculationState.Empty)
        private set

    override fun calculate(params: InputParameters) {
        val (roomLength, roomHeight, doorsAndWindowsSquare, blockSize) = params

        val V1: Double = blockSize.length.toDouble() * blockSize.height.toDouble() * blockSize.depth.toDouble() / 1000000000.toDouble()

        val blocksQuantityQm = (roomLength * roomHeight - doorsAndWindowsSquare) * 1.05 * blockSize.depth / 1000

        //val blocksQuantityQm = V // fmt #.##
        val blocksQuantity = (blocksQuantityQm / V1).toInt()

        var Vk: Double
        var pal: Double

        if (blockSize.length == 600) {
            pal = ceil(blocksQuantityQm / 1.8)
            Vk = pal * 1.8
        } else {
            pal = Math.ceil(blocksQuantityQm / 1.875)
            Vk = pal * 1.875
            if (blockSize.depth == 400) {
                pal = ceil(blocksQuantityQm / 2)
                Vk = pal * 2
            }
        }

        val blocksQuantityMultipleOfAPalletInQubicMeters = Vk // fmt #.##
        val blocksQuantityMultipleOfAPallet = Vk / V1

        val qpal = 1.8 / V1

        result = CalculationState.Success(
            blocksQuantity = blocksQuantity,
            blocksQuantityInQubicMeters = blocksQuantityQm,
            blocksQuantityMultipleOfAPallet = blocksQuantityMultipleOfAPallet.roundToInt(),
            blocksQuantityMultipleOfAPalletInQubicMeters = blocksQuantityMultipleOfAPalletInQubicMeters,
            blocksQuantityPerPallet = qpal.roundToInt(),
            palletsQuantity = pal.toInt()
        )
    }

    fun calculate(
        length: String,
        height: String,
        doorsAndWindowsSquare: String,
        blockSize: BlockSize
    ) {
        val heightAsFloat = try {
            height.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongHeight
            return
        }

        val lengthAsFloat = try {
            length.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongLength
            return
        }

        val doorsAndWindowsSquareAsFloat = try {
            doorsAndWindowsSquare.toFloat()
        } catch (ex: NumberFormatException) {
            result = CalculationState.WrongDoorsAndWindowsSquare
            return
        }

        val params = InputParameters(
            height = heightAsFloat,
            length = lengthAsFloat,
            doorsAndWindowsSquare = doorsAndWindowsSquareAsFloat,
            blockSize = blockSize
        )

        calculate(params)
    }

    fun clear() {
        result = CalculationState.Empty
    }
}

data class BlockSize(
    val length: Int,
    val height: Int,
    val depth: Int
) {
    constructor(dimension: String) : this(
        dimension.split("x")[0].toInt(),
        dimension.split("x")[1].toInt(),
        dimension.split("x")[2].toInt()
    )

    override fun toString(): String {
        return "${length}x${height}x${depth}"
    }
}

data class InputParameters(
    val length: Float,
    val height: Float,
    val doorsAndWindowsSquare: Float,
    val blockSize: BlockSize
)

interface CalculationState {

    /**
     * @param blocksQuantity Количество блоков в штуках
     * @param blocksQuantityInQubicMeters Количество блоков в метрах кубических
     * @param blocksQuantityMultipleOfAPallet Количество блоков кратное паллете в штуках
     * @param blocksQuantityMultipleOfAPalletInQubicMeters Количество блоков кратное паллете в метрах кубических
     * @param blocksQuantityPerPallet Количество блоков на паллете
     * @param palletsQuantity Количество паллет
     */
    data class Success(
        val blocksQuantity: Int,
        val blocksQuantityInQubicMeters: Double,

        val blocksQuantityMultipleOfAPallet: Int,
        val blocksQuantityMultipleOfAPalletInQubicMeters: Double,

        val blocksQuantityPerPallet: Int,
        val palletsQuantity: Int,
    ) : CalculationState

    object WrongHeight : CalculationState

    object WrongLength : CalculationState

    object WrongDoorsAndWindowsSquare : CalculationState

    object Empty : CalculationState
}

/*
<select id="dim" name="dim" class="sc_select_enable">
  <option value="1">600x250x500</option>
  <option value="2">600x250x375</option>
  <option value="3">600x250x300</option>
  <option value="4">600x250x250</option>
  <option value="5">600x200x500</option>
  <option value="6">600x200x375</option>
  <option value="7">600x200x300</option>
  <option value="8">600x200x250</option>
  <option value="9">600x200x150</option>
  <option value="10">600x250x100</option>
  <option value="11">600x250x75</option>
  <option value="12">600x250x150</option>
  <option value="13">600x200x100</option>
  <option value="14">600x200x75</option>
  <option value="15">625x250x500</option>
  <option value="16">625x250x375</option>
  <option value="17">625x250x300</option>
  <option value="18">625x250x250</option>
  <option value="19">625x200x500</option>
  <option value="20">625x200x375</option>
  <option value="21">625x200x300</option>
  <option value="22">625x200x250</option>
  <option value="23">625x200x150</option>
  <option value="24">625x250x100</option>
  <option value="25">625x250x75</option>
  <option value="26">625x250x150</option>
  <option value="27">625x200x100</option>
  <option value="28">625x200x75</option>
  <option value="29">625x200x400</option>
  <option value="30">625x250x400</option>
</select>
 */

/*
minpal=1;
mpal=1.8;
time=1000;


$(document).ready(function(){

   $("#count").click(function(){
       var blocks=new Array();
       blocks[1]=new Array();	blocks[1]["l"]=600;	blocks[1]["h"]=250;	blocks[1]["d"]=500;
       blocks[2]=new Array();  blocks[2]["l"]=600;	blocks[2]["h"]=250;	blocks[2]["d"]=375;
       blocks[3]=new Array();	blocks[3]["l"]=600;	blocks[3]["h"]=250;	blocks[3]["d"]=300;
       blocks[4]=new Array();	blocks[4]["l"]=600;	blocks[4]["h"]=250;	blocks[4]["d"]=250;
       blocks[5]=new Array();	blocks[5]["l"]=600;	blocks[5]["h"]=200;	blocks[5]["d"]=500;
       blocks[6]=new Array();	blocks[6]["l"]=600;	blocks[6]["h"]=200;	blocks[6]["d"]=375;
       blocks[7]=new Array();	blocks[7]["l"]=600;	blocks[7]["h"]=200;	blocks[7]["d"]=300;
       blocks[8]=new Array();	blocks[8]["l"]=600;	blocks[8]["h"]=200;	blocks[8]["d"]=250;
       blocks[9]=new Array();	blocks[9]["l"]=600;	blocks[9]["h"]=250;	blocks[9]["d"]=150;
       blocks[10]=new Array();	blocks[10]["l"]=600;	blocks[10]["h"]=250;	blocks[10]["d"]=100;
       blocks[11]=new Array();	blocks[11]["l"]=600;	blocks[11]["h"]=250;	blocks[11]["d"]=75;
       blocks[12]=new Array();	blocks[12]["l"]=600;	blocks[12]["h"]=200;	blocks[12]["d"]=150;
       blocks[13]=new Array();	blocks[13]["l"]=600;	blocks[13]["h"]=200;	blocks[13]["d"]=100;
       blocks[14]=new Array();	blocks[14]["l"]=600;	blocks[14]["h"]=200;	blocks[14]["d"]=75;

       blocks[15]=new Array();	blocks[15]["l"]=625;	blocks[15]["h"]=250;	blocks[15]["d"]=500;
       blocks[16]=new Array(); blocks[16]["l"]=625;	blocks[16]["h"]=250;	blocks[16]["d"]=375;
       blocks[17]=new Array();	blocks[17]["l"]=625;	blocks[17]["h"]=250;	blocks[17]["d"]=300;
       blocks[18]=new Array();	blocks[18]["l"]=625;	blocks[18]["h"]=250;	blocks[18]["d"]=250;
       blocks[19]=new Array();	blocks[19]["l"]=625;	blocks[19]["h"]=200;	blocks[19]["d"]=500;
       blocks[20]=new Array();	blocks[20]["l"]=625;	blocks[20]["h"]=200;	blocks[20]["d"]=375;
       blocks[21]=new Array();	blocks[21]["l"]=625;	blocks[21]["h"]=200;	blocks[21]["d"]=300;
       blocks[22]=new Array();	blocks[22]["l"]=625;	blocks[22]["h"]=200;	blocks[22]["d"]=250;
       blocks[23]=new Array();	blocks[23]["l"]=625;	blocks[23]["h"]=250;	blocks[23]["d"]=150;
       blocks[24]=new Array();	blocks[24]["l"]=625;	blocks[24]["h"]=250;	blocks[24]["d"]=100;
       blocks[25]=new Array();	blocks[25]["l"]=625;	blocks[25]["h"]=250;	blocks[25]["d"]=75;
       blocks[26]=new Array();	blocks[26]["l"]=625;	blocks[26]["h"]=200;	blocks[26]["d"]=150;
       blocks[27]=new Array();	blocks[27]["l"]=625;	blocks[27]["h"]=200;	blocks[27]["d"]=100;
       blocks[28]=new Array();	blocks[28]["l"]=625;	blocks[28]["h"]=200;	blocks[28]["d"]=75;
       blocks[29]=new Array();	blocks[29]["l"]=625;	blocks[29]["h"]=250;	blocks[29]["d"]=400;
       blocks[30]=new Array();	blocks[30]["l"]=625;	blocks[30]["h"]=200;	blocks[30]["d"]=400;


       var L=$("#len").val();
       var H=$("#height").val();
       var Spr=$("#square").val();
       var Nbl=$("#dim option:selected").val();

       var block=new Array(); block=blocks[Nbl];
       var V1=block["l"]*block["h"]*block["d"]/1000000000;
       var V=(L*H-Spr)*1.05*block["d"]/1000;

       $("#q_m").val(number_format(V, {thousands_sep: " ", dec_point: ".", decimals: 2}));
       $("#q_s").val(number_format(V/V1, {thousands_sep: " ", dec_point: ".", decimals: 0}));

       if (block["l"]==600) {
           var pal=Math.ceil(V/1.8);
           var Vk=pal*1.8;
       }
       else {
           var pal=Math.ceil(V/1.875);
           var Vk=pal*1.875;
           if (block["d"]==400) {
               pal=Math.ceil(V/2);
               Vk=pal*2;
           }
       }

       $("#qk_m").val(number_format(Vk, {thousands_sep: " ", dec_point: ".", decimals: 2}));
       $("#qk_s").val(number_format(Vk/V1, {thousands_sep: " ", dec_point: ".", decimals: 0}));

       $("#pal").val(pal);
       $("#qpal").val(number_format(1.8/V1, {thousands_sep: " ", dec_point: ".", decimals: 0}));


   });

});

               //--><!]]>
 */