const model = require('../models/tarefa');
module.exports = {
    async salvar (req, res) { //Funcao que retorna serializa o objeto passado por parametro
        const descricao = req.body.descricao;
        console.log(req.body.descricao);
        const obj = await model.create({
            descricao,
            flag: false
        });
        return res.json(obj);
    },
    async buscarTodas (req, res) { //Funcao que retorna serializa o objeto passado por parametro
        const descricao = req.body.descricao;
        console.log(req.body.descricao);
        const obj = await model.findAll();
        return res.json(obj);
    },
    async buscarPendentes (req, res) { //Funcao que retorna serializa o objeto passado por parametro
        const descricao = req.body.descricao;
        console.log(req.body.descricao);
        const obj = await model.findAll({
            where: {
                flag: false
            }
        });
        return res.json(obj);
    }
    ,
    async updateImg(req, res){
       const { img64} = req.body;
       const {id} = req.params;
       const tarefa = await model.findByPk(id);
       tarefa.img64 = img64;
       console.log(tarefa);
       tarefa.flag = true;
       await tarefa.save();
        return res.json(tarefa);
    }


}